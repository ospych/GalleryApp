package com.example.galleryapp.ui.fragments.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.galleryapp.core.services.models.PhotoResponse
import com.example.galleryapp.logic.repositories.ImageRepositoryImpl
import com.example.galleryapp.ui.fragments.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val repo: ImageRepositoryImpl) : BaseViewModel() {
    private val _images = MutableLiveData<PagingData<PhotoResponse>>()
    val images: LiveData<PagingData<PhotoResponse>>
        get() = _images

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        loadImages()
    }

    fun loadImages() {
        setIsLoading(true)
        viewModelScope.launch {
            val result = repo.getImagesState()
            if (result is PagingSource.LoadResult.Error) {
                _error.postValue(result.throwable.message)
                setIsLoading(false)
            } else if (result is PagingSource.LoadResult.Page) {
                setIsLoading(false)
                val flow = repo.getImages().cachedIn(viewModelScope)
                flow.collectLatest { pagingData ->
                    _images.postValue(pagingData)
                }
            }
        }
    }
}
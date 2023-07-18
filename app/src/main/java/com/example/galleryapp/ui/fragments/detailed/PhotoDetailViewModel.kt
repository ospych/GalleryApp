package com.example.galleryapp.ui.fragments.detailed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.core.network.utils.ResponseResult
import com.example.galleryapp.core.services.models.PhotoResponse
import com.example.galleryapp.logic.repositories.ImageRepositoryImpl
import com.example.galleryapp.ui.fragments.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val repo: ImageRepositoryImpl
    ) : BaseViewModel() {
    private val _image = MutableLiveData<ResponseResult<PhotoResponse?>>()
    val image: LiveData<ResponseResult<PhotoResponse?>>
        get() = _image

    fun getImageById(id: String) {
        setIsLoading(true)
        viewModelScope.launch {
            _image.postValue(repo.getImage(id))
            setIsLoading(false)
        }
    }
}
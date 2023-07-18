package com.example.galleryapp.ui.fragments.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected fun setIsLoading(loading: Boolean) {
        _isLoading.postValue(loading)
    }
}
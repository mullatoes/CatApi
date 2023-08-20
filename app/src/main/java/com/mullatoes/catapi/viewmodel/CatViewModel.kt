package com.mullatoes.catapi.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mullatoes.catapi.model.CatImage
import com.mullatoes.catapi.repository.CatRepository

class CatViewModel(private val savedStateHandle: Context,
                   private val repository: CatRepository
) : ViewModel() {

    private val _catImages = MutableLiveData<List<CatImage>>()
    val catImages: LiveData<List<CatImage>> = _catImages

    suspend fun fetchCatImages(
        apiKey: String, limit: Int
    ){
        val images = repository.getCatImages(apiKey, limit)
        _catImages.postValue(images)
    }
}
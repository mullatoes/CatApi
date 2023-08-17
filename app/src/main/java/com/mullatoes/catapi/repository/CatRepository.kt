package com.mullatoes.catapi.repository

import com.mullatoes.catapi.model.CatImage
import com.mullatoes.catapi.service.CatApiService

class CatRepository(
    private val service: CatApiService
) {

    suspend fun getCatImages(apiKey: String, limit: Int): List<CatImage> {
        return service.getCatImages(apiKey, limit)
    }
}
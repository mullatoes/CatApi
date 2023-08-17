package com.mullatoes.catapi.service

import com.mullatoes.catapi.model.CatImage
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatApiService {
    @GET("v1/images/search")
    suspend fun getCatImages(@Header("x-api-key") apiKey: String, @Query("limit") limit: Int): List<CatImage>

}
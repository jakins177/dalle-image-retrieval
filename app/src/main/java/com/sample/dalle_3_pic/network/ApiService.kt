package com.sample.dalle_3_pic.network

import com.sample.dalle_3_pic.model.ImageGenerationRequest
import com.sample.dalle_3_pic.model.ResponseData
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {

    @POST("v1/images/generations")
    suspend fun generateImage(@Body request: ImageGenerationRequest): Response<ResponseData>
}
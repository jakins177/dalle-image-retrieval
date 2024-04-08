package com.sample.dalle_3_pic.model

data class ImageGenerationRequest(
    val model: String,
    val prompt: String,
    val n: Int,
    val size: String
)

package com.sample.dalle_3_pic.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.dalle_3_pic.model.ImageGenerationRequest
import com.sample.dalle_3_pic.model.ResponseData
import com.sample.dalle_3_pic.network.AIIG_RetrofitInstance
import kotlinx.coroutines.launch

class AIIG_ViewModel : ViewModel() {
    private val _images = MutableLiveData<ResponseData>()
    val images: LiveData<ResponseData> = _images

    private val TAG = "com.sample.api_example_with_compose.viewmodel.AIIG_ViewModel"

    fun fetchImages(model: String, prompt: String, n: Int, size: String) {
        viewModelScope.launch {
            try {
                val request = ImageGenerationRequest(model, prompt, n, size)
                val response = AIIG_RetrofitInstance.AIIG_Api.AIIG_RetrofitService.generateImage(request)

                if (response.isSuccessful) {
                    Log.i(TAG, "fetchImages: success, the response body is ${response.body()}")
                    val responseData = response.body()

                    if (responseData != null) {
                        _images.value = responseData
                        Log.i(TAG, "fetchImages: images.value is ${_images.value}")
                    }
                }
            } catch (e: Exception) {
                // Handle error
                Log.e(TAG, "fetchImages error: ${e.message}", e)
            }
        }
    }
}
package com.sample.dalle_3_pic.network
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object AIIG_RetrofitInstance {
    private const val BASE_URL = "https://api.openai.com/"
    private const val API_KEY = ""

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request: Request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    object AIIG_Api {
        val AIIG_RetrofitService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }

    }


}
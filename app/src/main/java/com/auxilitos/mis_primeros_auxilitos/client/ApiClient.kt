package com.auxilitos.mis_primeros_auxilitos.client

import com.auxilitos.mis_primeros_auxilitos.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    const val baseUrl = "http://192.168.10.79:8000/" // Reemplaza esto con tu baseUrl

    private fun getRetrofit(): Retrofit {

        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(baseUrl)
            .build()
    }

    fun getApiService(): ApiService{
        return getRetrofit().create(ApiService::class.java)
    }

}
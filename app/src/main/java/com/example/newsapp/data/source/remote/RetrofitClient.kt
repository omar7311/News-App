package com.example.newsapp.data.source.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitClient {
    companion object{
        private final const val BASE_URL="https://newsapi.org/v2/"
        private lateinit var retrofit:Retrofit
       fun getWebServices():WebServices{
            retrofit=Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(WebServices::class.java)
        }
    }
}
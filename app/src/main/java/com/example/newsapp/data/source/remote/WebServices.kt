package com.example.newsapp.data.source.remote

import com.example.newsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("top-headlines")
    suspend fun getCountryNews(@Query("country") country:String,@Query("apiKey") apiKey:String): Response<NewsResponse>
    @GET("everything")
    suspend fun getLatestNews(@Query("domains") domains:String,@Query("apiKey") apiKey:String):Response<NewsResponse>
    @GET("everything")
    suspend fun getNews(@Query("q") query:String,@Query("apiKey") apiKey:String):Response<NewsResponse>
}
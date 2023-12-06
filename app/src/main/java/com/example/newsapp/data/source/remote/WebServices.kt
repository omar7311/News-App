package com.example.newsapp.data.source.remote

import com.example.newsapp.data.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("top-headlines")
    fun getCountryNews(@Query("country") country:String,@Query("apiKey") apiKey:String):Call<NewsResponse>
    @GET("everything")
    fun getLatestNews(@Query("domains") domains:String,@Query("apiKey") apiKey:String):Call<NewsResponse>
    @GET("everything")
    fun getNews(@Query("q") query:String,@Query("apiKey") apiKey:String):Call<NewsResponse>
}
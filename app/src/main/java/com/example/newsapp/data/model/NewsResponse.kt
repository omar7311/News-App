package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int?=null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem>?=null,

	@field:SerializedName("status")
	val status: String?=null
)

data class ArticlesItem(

	@field:SerializedName("publishedAt")
	 var  publishedAt: String?=null,

	@field:SerializedName("author")
	val author: String?=null,

	@field:SerializedName("urlToImage")
	val urlToImage: Any?=null,

	@field:SerializedName("description")
	val description: Any?=null,

	@field:SerializedName("source")
	val source: Source?=null,

	@field:SerializedName("title")
	val title: String?=null,

	@field:SerializedName("url")
	val url: String?=null,

	@field:SerializedName("content")
	val content: Any?=null
):Serializable

data class Source(

	@field:SerializedName("name")
	val name: String?=null,

	@field:SerializedName("id")
	val id: String?=null
)

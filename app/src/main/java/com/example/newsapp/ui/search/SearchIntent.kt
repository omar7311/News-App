package com.example.newsapp.ui.search

import com.example.newsapp.data.model.ArticlesItem

sealed class SearchIntent{
    data class getNews(var query: String): SearchIntent()
    data class getNewsDetails(var articlesItem: ArticlesItem): SearchIntent()
    data class AddBookMark(var articlesItem: ArticlesItem): SearchIntent()
}

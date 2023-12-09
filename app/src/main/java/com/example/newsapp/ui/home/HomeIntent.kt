package com.example.newsapp.ui.home

import com.example.newsapp.data.model.ArticlesItem

sealed class HomeIntent{
   data class getNewsDetails(var articlesItem: ArticlesItem): HomeIntent()
    data class AddBookMark(var articlesItem: ArticlesItem): HomeIntent()
}

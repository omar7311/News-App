package com.example.newsapp.ui

import com.example.newsapp.data.model.ArticlesItem

interface NewsAction {
    fun newsClicked(news: ArticlesItem)

}
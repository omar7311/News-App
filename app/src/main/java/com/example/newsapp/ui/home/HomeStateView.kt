package com.example.newsapp.ui.home

import com.example.newsapp.data.model.ArticlesItem

sealed class HomeStateView{
     class Result(var resultEgyptNews: ArrayList<ArticlesItem>?, var resultLatestNews:ArrayList<ArticlesItem>?):
          HomeStateView()
     class Loading(): HomeStateView()
     class Error(): HomeStateView()
}

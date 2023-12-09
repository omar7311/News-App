package com.example.newsapp.ui.bookmark

import com.example.newsapp.data.model.ArticlesItem

sealed class BookmarkIntent{
    data class getNewsDetails(var articlesItem: ArticlesItem): BookmarkIntent()
}

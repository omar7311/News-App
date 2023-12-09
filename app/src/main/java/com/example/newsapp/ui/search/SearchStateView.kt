package com.example.newsapp.ui.search

import com.example.newsapp.data.model.ArticlesItem

sealed class SearchStateView {
    class Result(
        var resultNews: ArrayList<ArticlesItem>?
    ) : SearchStateView()

    class Loading() : SearchStateView()
    class Error() : SearchStateView()
}

package com.example.newsapp.ui

import androidx.core.content.ContextCompat.getString
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.data.source.remote.RetrofitClient
import com.example.newsapp.ui.bookmark.BookmarkFragment
import com.example.newsapp.ui.bookmark.BookmarkFragmentDirections
import com.example.newsapp.ui.bookmark.BookmarkIntent
import com.example.newsapp.ui.home.HomeFragment
import com.example.newsapp.ui.home.HomeFragmentDirections
import com.example.newsapp.ui.home.HomeIntent
import com.example.newsapp.ui.home.HomeStateView
import com.example.newsapp.ui.search.SearchFragment
import com.example.newsapp.ui.search.SearchFragmentDirections
import com.example.newsapp.ui.search.SearchIntent
import com.example.newsapp.ui.search.SearchStateView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    lateinit var mainFragment: Fragment

    val homeChannel = Channel<HomeIntent>()
    private val homeStateFlow =
        MutableStateFlow<HomeStateView>(HomeStateView.Loading())
    val homeFlow: MutableStateFlow<HomeStateView> get() = homeStateFlow

    val searchChannel = Channel<SearchIntent>()
    private val searchStateFlow =
        MutableStateFlow<SearchStateView>(SearchStateView.Result(arrayListOf()))
    val searchFlow: MutableStateFlow<SearchStateView> get() = searchStateFlow
    val bookmarkChannel = Channel<BookmarkIntent>()
    init {
        model()
        getResult()
    }

     fun model() {
        viewModelScope.launch {
            homeChannel.consumeAsFlow().collect {
                when (it) {
                    is HomeIntent.getNewsDetails -> getNewsDetails(it.articlesItem, mainFragment)
                    is HomeIntent.AddBookMark -> addToBookmark(it.articlesItem)
                }
            }
        }
        viewModelScope.launch {
            searchChannel.consumeAsFlow().collect {
                when (it) {
                    is SearchIntent.getNews-> getNews(it.query)
                    is SearchIntent.getNewsDetails-> getNewsDetails(it.articlesItem, mainFragment)
                    is SearchIntent.AddBookMark-> addToBookmark(it.articlesItem)
                }
            }
        }
        viewModelScope.launch {
            bookmarkChannel.consumeAsFlow().collect {
                when (it) {
                    is BookmarkIntent.getNewsDetails -> getNewsDetails(it.articlesItem, mainFragment)

                }
            }
        }
    }
 private fun getNews(query: String){
     searchStateFlow.value= SearchStateView.Loading()
     GlobalScope.launch(Dispatchers.IO) {
         val response =
             RetrofitClient.getWebServices().getNews(query, getString(mainFragment.requireContext(),R.string.api_Key))
         if (response.isSuccessful) {
             searchStateFlow.value= SearchStateView.Result(response.body()!!.articles as ArrayList<ArticlesItem>)
         }else{
             searchStateFlow.value= SearchStateView.Error()
         }
     }
 }
    private fun addToBookmark(item: ArticlesItem) {
        BookmarkFragment.bookmarkList.add(item)
    }

    private fun getNewsDetails(item: ArticlesItem, mainFragment: Fragment) {
        when (mainFragment) {
            is HomeFragment -> Navigation.findNavController(mainFragment.requireView())
                .navigate(HomeFragmentDirections.actionHomeToNewsDetailsFragment(item))

            is SearchFragment -> Navigation.findNavController(mainFragment.requireView())
                .navigate(SearchFragmentDirections.actionSearchToNewsDetailsFragment(item))

            is BookmarkFragment -> Navigation.findNavController(mainFragment.requireView())
                .navigate(BookmarkFragmentDirections.actionBookmarkToNewsDetailsFragment2(item))
        }

    }


    private fun getResult() {
        viewModelScope.launch(Dispatchers.IO) {
            val responseEgyptNews = RetrofitClient.getWebServices()
                .getCountryNews("eg", getString(mainFragment.requireActivity(), R.string.api_Key))
            val responseLatestNews = RetrofitClient.getWebServices()
                .getLatestNews(
                    "bbc.com,thenextweb",
                    getString(mainFragment.requireActivity(), R.string.api_Key)
                )
            if (responseEgyptNews.isSuccessful&&responseLatestNews.isSuccessful) {
                homeStateFlow.value =
                   HomeStateView.Result(responseEgyptNews.body()!!.articles as ArrayList<ArticlesItem>
                       ,responseLatestNews.body()!!.articles as ArrayList<ArticlesItem>)

            } else {
                homeStateFlow.value = HomeStateView.Error()
            }
        }
    }




}
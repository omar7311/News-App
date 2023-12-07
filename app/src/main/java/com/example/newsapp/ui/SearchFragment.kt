package com.example.newsapp.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.newsapp.R
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.source.remote.RetrofitClient
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.ui.adapter.LatestNewsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(), NewsAction {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: LatestNewsAdapter
    lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        init()
        binding.searchButton.setOnClickListener {
            if (binding.search.text.toString().isNotBlank()) {
                dialog.show()
                    getNews(binding.search.text.toString().toLowerCase())

            }
        }

    }

    fun init() {
        dialog = ProgressDialog(context)
        dialog.setTitle("loading")
        adapter = LatestNewsAdapter(this)
        binding.searchRecycleView.layoutManager = LinearLayoutManager(context)
    }

    fun getNews(query: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                RetrofitClient.getWebServices().getNews(query, getString(R.string.api_Key))
            if (response.isSuccessful) {
                adapter.addList(response.body()!!.articles as ArrayList<ArticlesItem>)
                withContext(Dispatchers.Main) {
                    binding.searchRecycleView.adapter = adapter
                    dialog.dismiss()
                    binding.search.setText("")
                }
            }
        }
    }

    override fun newsClicked(news: ArticlesItem) {
        Navigation.findNavController(binding.root)
            .navigate(SearchFragmentDirections.actionSearchToNewsDetailsFragment(news))
    }

}
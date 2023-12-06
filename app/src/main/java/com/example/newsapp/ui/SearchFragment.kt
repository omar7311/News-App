package com.example.newsapp.ui

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(),LatestNewsAdapter.NewsAction {
lateinit var binding: FragmentSearchBinding
lateinit var adapter: LatestNewsAdapter

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
        binding=FragmentSearchBinding.bind(view)
        init()
        binding.searchButton.setOnClickListener {
            if(binding.search.text.toString().isNotBlank()){
             getNews(binding.search.text.toString().toLowerCase().trim())}

        }

    }
    fun init() {
        adapter= LatestNewsAdapter(this)
        binding.searchRecycleView.layoutManager=LinearLayoutManager(context)
    }
    fun getNews(query:String){
        RetrofitClient.getWebServices().getNews(query,getString(R.string.api_Key))
            .enqueue(object :Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    adapter.addList(response.body()!!.articles as ArrayList<ArticlesItem>)
                    binding.searchRecycleView.adapter=adapter
                    binding.search.setText("")
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }

    override fun newsClicked(news: ArticlesItem) {
        Navigation.findNavController(binding.root)
            .navigate(SearchFragmentDirections.actionSearchToNewsDetailsFragment(news))
    }

}
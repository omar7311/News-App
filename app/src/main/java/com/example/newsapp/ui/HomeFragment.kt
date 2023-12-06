package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.source.remote.RetrofitClient
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.adapter.EgyptNewsAdapter
import com.example.newsapp.ui.adapter.LatestNewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter1: EgyptNewsAdapter
    lateinit var adapter2: LatestNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentHomeBinding.bind(view)
        init()
       getEgyptNews()
        getLatestNews()
    }

    private fun getLatestNews() {
            RetrofitClient.getWebServices().getLatestNews("bbc.com,thenextweb.com",getString(R.string.api_Key))
                .enqueue(object :Callback<NewsResponse>{
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                       adapter2.addList(response.body()!!.articles as ArrayList<ArticlesItem>)
                        binding.recycleView2.adapter=adapter2
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
    }

    fun init(){
        adapter1= EgyptNewsAdapter()
        adapter2= LatestNewsAdapter()
        binding.recycleView2.layoutManager=LinearLayoutManager(context)
        binding.recycleView1.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }
    fun getEgyptNews(){
      RetrofitClient.getWebServices()
          .getCountryNews("eg",getString(R.string.api_Key))
          .enqueue(object :Callback<NewsResponse>{
              override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                  Log.d("tag",response.body()!!.articles.toString())
                  adapter1.addList(response.body()!!.articles as ArrayList<ArticlesItem>)
                  binding.recycleView1.adapter=adapter1
              }
              override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                  TODO("Not yet implemented")
              }

          })

   }


}
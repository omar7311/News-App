package com.example.newsapp.ui

import android.app.ProgressDialog
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), NewsAction {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter1: EgyptNewsAdapter
    lateinit var adapter2: LatestNewsAdapter
    lateinit var dialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        init()
        getEgyptNews()
        getLatestNews()
    }

    fun init() {
        dialog = ProgressDialog(context)
        dialog.setTitle("loading....")
        dialog.show()
        adapter1 = EgyptNewsAdapter()
        adapter2 = LatestNewsAdapter(this)


        binding.recycleView2.layoutManager = LinearLayoutManager(context)
        binding.recycleView1.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    fun getLatestNews() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.getWebServices()
                .getLatestNews("bbc.com,thenextweb.com", getString(R.string.api_Key))
            if (response.isSuccessful) {
                adapter2.addList(response.body()!!.articles as ArrayList<ArticlesItem>)
                withContext(Dispatchers.Main) {
                    binding.recycleView2.adapter = adapter2
                    dialog.dismiss()
                }
            }
        }
    }


    fun getEgyptNews() {
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                RetrofitClient.getWebServices().getCountryNews("eg", getString(R.string.api_Key))
            if (response.isSuccessful) {
                adapter1.addList(response.body()!!.articles as ArrayList<ArticlesItem>)
                withContext(Dispatchers.Main) {
                    binding.recycleView1.adapter = adapter1
                    dialog.dismiss()
                }
            }
        }
    }

    override fun newsClicked(news: ArticlesItem) {
        Navigation.findNavController(binding.root)
            .navigate(HomeFragmentDirections.actionHomeToNewsDetailsFragment(news))
    }


}
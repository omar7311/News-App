package com.example.newsapp.ui.search

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.ui.MainViewModel
import com.example.newsapp.ui.adapter.LatestNewsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: LatestNewsAdapter
    lateinit var dialog: ProgressDialog
    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.mainFragment = this
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
        sender()
        view()
    }

    fun init() {
        dialog = ProgressDialog(context)
        dialog.setTitle("loading")
        adapter = LatestNewsAdapter(viewModel)
        binding.searchRecycleView.layoutManager = LinearLayoutManager(context)
    }

    fun sender() {
        binding.searchButton.setOnClickListener {
            if (binding.search.text.toString().isNotBlank()) {
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.searchChannel.send(SearchIntent.getNews(binding.search.text.toString()))
                }
            }
        }
    }

    fun view() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.searchFlow.collect {
                when (it) {
                    is SearchStateView.Result -> {
                        adapter.addList(it.resultNews!!)
                        binding.searchRecycleView.adapter = adapter
                        dialog.dismiss()
                    }
                    is SearchStateView.Loading -> dialog.show()
                    is SearchStateView.Error -> Toast.makeText(context, "Error", Toast.LENGTH_LONG)
                        .show()

                }
            }
        }
    }


}
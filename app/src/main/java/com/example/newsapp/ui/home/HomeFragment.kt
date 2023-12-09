package com.example.newsapp.ui.home

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.MainViewModel
import com.example.newsapp.ui.adapter.EgyptNewsAdapter
import com.example.newsapp.ui.adapter.LatestNewsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter1: EgyptNewsAdapter
    lateinit var adapter2: LatestNewsAdapter
    lateinit var dialog: ProgressDialog
    val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.mainFragment = this
        super.onCreate(savedInstanceState)
    }

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
        view()
    }

    fun init() {
        dialog = ProgressDialog(context)
        dialog.setTitle("loading....")
        adapter1 = EgyptNewsAdapter(viewModel)
        adapter2 = LatestNewsAdapter(viewModel)
        binding.recycleView2.layoutManager = LinearLayoutManager(context)
        binding.recycleView1.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun view() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.homeFlow.collect {
                when (it) {
                    is HomeStateView.Result -> {
                        adapter1.addList(it.resultEgyptNews!!)
                        binding.recycleView1.adapter = adapter1
                        adapter2.addList(it.resultLatestNews!!)
                        binding.recycleView2.adapter = adapter2
                        dialog.dismiss()
                    }

                    is HomeStateView.Loading -> dialog.show()

                    is HomeStateView.Error -> {
                        dialog.dismiss()
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

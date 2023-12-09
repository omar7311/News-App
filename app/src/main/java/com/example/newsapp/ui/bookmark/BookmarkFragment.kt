package com.example.newsapp.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.databinding.FragmentBookmarkBinding
import com.example.newsapp.ui.MainViewModel
import com.example.newsapp.ui.adapter.LatestNewsAdapter


class BookmarkFragment : Fragment() {
    lateinit var binding:FragmentBookmarkBinding
    lateinit var adapter: LatestNewsAdapter

    val viewModel  by lazy { ViewModelProvider(this).get(MainViewModel::class.java)}

    companion object{
    var bookmarkList=ArrayList<ArticlesItem>()
}
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.mainFragment=this
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding=FragmentBookmarkBinding.bind(view)
        init()
        adapter.addList(bookmarkList)
        super.onViewCreated(view, savedInstanceState)
    }
    fun init(){
        adapter= LatestNewsAdapter(viewModel)
        binding.recycleView3.adapter=adapter
        binding.recycleView3.layoutManager=LinearLayoutManager(context)
    }



}
package com.example.newsapp.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.databinding.FragmentBookmarkBinding
import com.example.newsapp.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : Fragment() {
lateinit var binding: FragmentNewsDetailsBinding
    lateinit var newsDetails: ArticlesItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null) {
             newsDetails= NewsDetailsFragmentArgs.fromBundle(requireArguments()).news

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding=FragmentNewsDetailsBinding.bind(view)
        bindUi()
        super.onViewCreated(view, savedInstanceState)
    }

fun bindUi(){
    binding.imageDetails.load(newsDetails.urlToImage)
    binding.titleDetails.text=newsDetails.title
    binding.timeDetails.text=newsDetails.publishedAt
    binding.description.text= newsDetails.description as String?
    binding.imageButtonDetail.setOnCheckedChangeListener { buttonView, isChecked ->
        if(isChecked) BookmarkFragment.bookmarkList.add(newsDetails)
    }
}
}
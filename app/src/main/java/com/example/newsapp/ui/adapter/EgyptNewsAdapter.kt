package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.databinding.ItemEgyptNewsBinding
import com.example.newsapp.ui.home.HomeIntent
import com.example.newsapp.ui.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EgyptNewsAdapter(var mainViewModel: MainViewModel) : RecyclerView.Adapter<EgyptNewsAdapter.NewsHolder>() {
    private var list:ArrayList<ArticlesItem>?=null

    fun addList(list: ArrayList<ArticlesItem>) {
        this.list = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding: ItemEgyptNewsBinding = ItemEgyptNewsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(binding)
    }

    override fun getItemCount(): Int {
        if(list==null) return 0
        else return list!!.size

    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        var currentItem: ArticlesItem = list!![position]
        holder.binding.title.text = currentItem.title
        holder.binding.time.text = currentItem.publishedAt
        holder.binding.image.load(currentItem.urlToImage)
        holder.binding.imageButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener
        { buttonView, isChecked ->
            if(isChecked){
                GlobalScope.launch {
                    mainViewModel.homeChannel.send(HomeIntent.AddBookMark(currentItem))
                } }
        })
    }

    inner class NewsHolder(var binding: ItemEgyptNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {
                    GlobalScope.launch {
                        mainViewModel.homeChannel.send(HomeIntent.getNewsDetails(list!![layoutPosition]))
                    }
                }
            }

    }
}












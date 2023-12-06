package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.data.model.ArticlesItem
import com.example.newsapp.databinding.LatestNewsItemBinding
import com.example.newsapp.ui.HomeFragmentDirections

class LatestNewsAdapter:RecyclerView.Adapter<LatestNewsAdapter.NewsHolder>() {
    private lateinit var list: ArrayList<ArticlesItem>
    fun addList(list: ArrayList<ArticlesItem>) {
        this.list =list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
       val binding=LatestNewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val curentItem=list[position]
        holder.binding.titleView.text=curentItem.title
        holder.binding.timeView.text=curentItem.publishedAt
        holder.binding.imageView.load(curentItem.urlToImage)
        holder.binding.imageButtonView.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{
            buttonView, isChecked ->
        })

    }
    inner class NewsHolder(val binding: LatestNewsItemBinding): RecyclerView.ViewHolder(binding.root) {
init {
    binding.root.setOnClickListener {
        Navigation.findNavController(binding.root)
            .navigate(HomeFragmentDirections.actionHomeToNewsDetailsFragment(list[layoutPosition]))
    }
}
    }
}
package com.saiful.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saiful.newsapp.R
import com.saiful.newsapp.constant.Constant
import com.saiful.newsapp.database.NewsArticle
import com.saiful.newsapp.viewmodel.NewsViewModel

class CardNewsAdapter(
    private val dataset: List<NewsArticle>,
    private val viewModel: NewsViewModel
) : RecyclerView.Adapter<CardNewsAdapter.CardNewsViewHolder>() {

    class CardNewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val newsTitle: TextView = view.findViewById(R.id.card_news_title)
        val newsDescription: TextView = view.findViewById(R.id.card_news_description)
        val newsBookmark: ImageView = view.findViewById(R.id.card_news_bookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardNewsViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_card, parent, false)
        return CardNewsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CardNewsViewHolder, position: Int) {
        val item = dataset[position]
        holder.newsTitle.text = item.title ?: "----"
        holder.newsDescription.text = item.description ?: "----"
        Glide
            .with(holder.itemView.context)
            .load(item.urlToImage)
            .centerCrop()
            .thumbnail(
                Glide.with(holder.itemView.context)
                    .load(R.drawable.search_gif)
            )
            .into(holder.itemView.findViewById(R.id.card_news_image));

//        details fragment action
        holder.itemView.setOnClickListener {
//            Log.d("TAG", "onBindViewHolder: click")
            Constant.newsArticle = item
//            Log.d("TAG", "onBindViewHolder: ${Global.newsArticle}")
            it.findNavController().navigate(R.id.newsArticleFragment)
        }

//        Bookmark button
        holder.newsBookmark.setOnClickListener {
//            Log.d("TAG", "onBindViewHolder: click")
            viewModel.addBookmarkNews(item)
        }

        if(item.isBookmark) {
            holder.newsBookmark.setImageResource(R.drawable.ic_bookmark)
        } else {
            holder.newsBookmark.setImageResource(R.drawable.ic_bookmark_border)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
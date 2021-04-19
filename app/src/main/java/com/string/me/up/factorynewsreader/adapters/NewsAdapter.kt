package com.string.me.up.factorynewsreader.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.string.me.up.factorynewsreader.R
import com.string.me.up.factorynewsreader.databinding.NewsCardBinding
import com.string.me.up.factorynewsreader.news.data.Article
import com.string.me.up.factorynewsreader.util.Helper

class NewsAdapter(
    private var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var newsList = ArrayList<Article>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    class NewsViewHolder(
        private val binding: NewsCardBinding,
        private val itemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(singleArticle: Article) {
            binding.run {
                Helper.loadRemoteImage(newsImageView, singleArticle.urlToImage, newsImageView)
                newsTitleTextView.text = singleArticle.title
            }
            itemView.setOnClickListener {
                itemClickListener.onItemClicked(
                    adapterPosition,
                    singleArticle.title
                )
            }

            binding.cardViewId.animation = AnimationUtils.loadAnimation(
                binding.cardViewId.context,
                R.anim.recycler_anim
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = DataBindingUtil.inflate<NewsCardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.news_card, parent, false
        )
        return NewsViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size
}

interface OnItemClickListener {
    fun onItemClicked(currentPosition: Int, currentTitle: String)
}
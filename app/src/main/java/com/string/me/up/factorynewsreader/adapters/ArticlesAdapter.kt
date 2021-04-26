package com.string.me.up.factorynewsreader.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.string.me.up.factorynewsreader.R
import com.string.me.up.factorynewsreader.databinding.ArticlesCardBinding
import com.string.me.up.factorynewsreader.news.data.Article
import com.string.me.up.factorynewsreader.util.Helper

class ArticlesAdapter(
    private val singleArticles: ArrayList<Article>
) : RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    fun updateArticles(updatedArticles: ArrayList<Article>) {
        singleArticles.clear()
        singleArticles.addAll(updatedArticles)
        notifyDataSetChanged()
    }

    class ArticlesViewHolder(private val binding: ArticlesCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(singleArticle: Article) {
            binding.run {
                Helper.loadRemoteImage(
                    articleImageView,
                    singleArticle.urlToImage,
                    articleImageView
                )
                articleTitleTextView.text = singleArticle.title
                articleBodyTextView.text = singleArticle.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val binding = DataBindingUtil.inflate<ArticlesCardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.articles_card, parent,
            false
        )
        return ArticlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) =
        holder.bind(singleArticles[position])

    override fun getItemCount(): Int = singleArticles.size
}
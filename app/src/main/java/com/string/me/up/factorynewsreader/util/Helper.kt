package com.string.me.up.factorynewsreader.util

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.string.me.up.factorynewsreader.R
import com.string.me.up.factorynewsreader.db.DbArticle
import com.string.me.up.factorynewsreader.news.data.Article
import com.string.me.up.factorynewsreader.news.data.NewsData
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

object Helper {
    const val TIME_OUT = 30000L

    //VALID API KEYS TO USE FOR TESTING (one of them should work)
//    const val API_KEY = "9e510090795445a9a0b93aa3275c3738"
    const val API_KEY = "5436d08829de4e09a2c88e93060e8d15"
//    const val API_KEY = "f2310ef81ecd4bfa9452636568a17319"
//    const val API_KEY = "266f58ae88e7457b93d1f6d15af5f574"
//    const val API_KEY = "0bd314d94e8744e4a85779bfb1d09514"

    fun displayDialog(message: Int, context: Context) {
        AlertDialog.Builder(context)
            .setTitle(R.string.error_title_label)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.positive_button_label) { _, _ ->
                exitProcess(0)
            }.create().show()
    }

    fun loadRemoteImage(view: View, url: String, imageView: ImageView) {
        Glide.with(view)
            .load(url)
            .into(imageView)
    }

    fun convert(data: String?): List<DbArticle?>? {
        return if (data == null) {
            listOf()
        } else {
            val listType: Type = object :
                TypeToken<List<DbArticle>>() {}.type
            Gson().fromJson<List<DbArticle?>>(data, listType)
        }
    }

    fun refreshTime() = TimeUnit.MINUTES.toMillis(5)

    fun Article.toDbArticle() = DbArticle(
        this.author,
        this.title,
        this.description,
        this.url,
        this.urlToImage,
        this.publishedAt
    )

    fun DbArticle.toArticle() = Article(
        this.author,
        this.title,
        this.description,
        this.url,
        this.urlToImage,
        this.publishedAt
    )

    fun Response<NewsData>.transform(): List<DbArticle>{
        return this.body()!!.articles.map{it.toDbArticle()}
    }

    sealed class State {
        class Success<T>(val response: T) : State()
        class Failure(val error: Int) : State()
    }
}

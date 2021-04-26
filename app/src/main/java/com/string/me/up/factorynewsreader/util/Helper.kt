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
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

object Helper {

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

    fun getRefreshTime() = TimeUnit.MINUTES.toMillis(5)
}

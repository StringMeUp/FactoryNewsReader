package com.string.me.up.factorynewsreader.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.string.me.up.factorynewsreader.db.DbArticle

object Converters {

    @TypeConverter
    fun convertObject(vararg dbArticle: DbArticle): String {
        return Gson().toJson(dbArticle)
    }

    @TypeConverter
    fun convertString(articlesData: String?): List<DbArticle?>? {
        return Helper.convert(articlesData)
    }
}
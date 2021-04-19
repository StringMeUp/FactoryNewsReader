package com.string.me.up.factorynewsreader.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbArticle(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String?,
    @ColumnInfo(name = "created_at")
    var createdAt: Long? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
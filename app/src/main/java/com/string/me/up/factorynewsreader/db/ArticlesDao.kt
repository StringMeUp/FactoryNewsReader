package com.string.me.up.factorynewsreader.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(dbArticles: List<DbArticle>)

    suspend fun insertWithTimestamp(dbArticles: List<DbArticle>) {
        insertAll(dbArticles.onEach { it.createdAt = System.currentTimeMillis() })
    }

    @Query("SELECT * FROM dbarticle")
    abstract suspend fun getArticles(): List<DbArticle>

    @Query("SELECT created_at FROM dbarticle")
    abstract suspend fun getTimeStamp(): Long?

    @Query("DELETE FROM dbarticle")
    abstract suspend fun delete()
}
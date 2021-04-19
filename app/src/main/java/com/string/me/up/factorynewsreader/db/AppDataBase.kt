package com.string.me.up.factorynewsreader.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.string.me.up.factorynewsreader.util.Converters

@Database(entities = [DbArticle::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): ArticlesDao
}
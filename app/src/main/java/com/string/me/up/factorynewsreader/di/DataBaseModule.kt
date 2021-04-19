package com.string.me.up.factorynewsreader.di

import android.content.Context
import androidx.room.Room
import com.string.me.up.factorynewsreader.R
import com.string.me.up.factorynewsreader.db.AppDataBase
import com.string.me.up.factorynewsreader.db.ArticlesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideArticlesDao(appDataBase: AppDataBase): ArticlesDao {
        return appDataBase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            appContext.getString(R.string.db_name_label)
        ).fallbackToDestructiveMigration().build()
    }
}
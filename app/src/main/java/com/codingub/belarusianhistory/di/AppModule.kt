package com.codingub.belarusianhistory.di

import android.app.Application
import androidx.room.Room
import com.codingub.belarusianhistory.App
import com.codingub.belarusianhistory.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {




    @Provides
    @Singleton
    fun provideItemDatabase(app: App): AppDatabase{
        return Room.databaseBuilder(
            app, AppDatabase::class.java, "history" //вот здесь
        ).createFromAsset("data/history") //и вот здесь
            .fallbackToDestructiveMigration()
            .build()
    }


}



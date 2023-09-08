package com.codingub.belarusianhistory.di

import android.app.Application
import androidx.room.Room
import com.codingub.belarusianhistory.App
import com.codingub.belarusianhistory.data.local.db.AppDatabase
import com.codingub.belarusianhistory.data.repository.AppRepositoryImpl
import com.codingub.belarusianhistory.domain.repository.AppRepository
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
    fun provideDbRepository(db: AppDatabase): AppRepository {
        return AppRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideItemDatabase(app: App): AppDatabase{
        return Room.databaseBuilder(
            app, AppDatabase::class.java, "history"
        ).createFromAsset("data/history")
            .fallbackToDestructiveMigration()
            .build()
    }


}



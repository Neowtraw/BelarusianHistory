package com.codingub.belarusianhistory.di

import android.content.Context
import androidx.room.Room
import com.codingub.belarusianhistory.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "results.db"
        ).fallbackToDestructiveMigration().build()
    }
//
//    @Provides
//    fun provideAppDao(
//        database: AppDatabase
//    ): RoomDao = database.dao()
}
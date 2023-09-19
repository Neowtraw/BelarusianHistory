package com.codingub.belarusianhistory.di

import androidx.room.Room
import com.codingub.belarusianhistory.App
import com.codingub.belarusianhistory.data.local.db.AppDatabase
import com.codingub.belarusianhistory.data.local.pref.ApplicationConfig
import com.codingub.belarusianhistory.data.repository.AppRepositoryImpl
import com.codingub.belarusianhistory.data.repository.ConfigSharedRepositoryImpl
import com.codingub.belarusianhistory.domain.repository.AppRepository
import com.codingub.belarusianhistory.domain.repository.ConfigSharedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*
            Db
         */

    @Provides
    @Singleton
    fun provideDbRepository(db: AppDatabase): AppRepository {
        return AppRepositoryImpl(db.ticketsDao, db.practiceQuestionDao,
            db.practiceAchievesDao, db.ticketQuestionDao, db.ticketAchievesDao)
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

    /*
        SharedPreferencies
     */

    @Provides
    @Singleton
    fun provideSharedPrefRepository(appConfig: ApplicationConfig) : ConfigSharedRepository{
        return ConfigSharedRepositoryImpl(appConfig)
    }

    @Provides
    @Singleton
    fun provideApplicationConfig() : ApplicationConfig{
        return ApplicationConfig()
    }

    @Provides
    @Singleton
    fun provideApp(): App {
        return App.getInstance()
    }


}



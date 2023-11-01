package com.codingub.belarusianhistory.di

import androidx.room.Room
import com.codingub.belarusianhistory.App
import com.codingub.belarusianhistory.BuildConfig
import com.codingub.belarusianhistory.data.local.db.AppDatabase
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.HistoryNetworking
import com.codingub.belarusianhistory.data.repository.AppRepositoryImpl
import com.codingub.belarusianhistory.domain.repository.AppRepository
import com.codingub.belarusianhistory.utils.Constants.Injection.BUILD_VERSION_CODE
import com.codingub.belarusianhistory.utils.Constants.Injection.BUILD_VERSION_NAME
import com.codingub.belarusianhistory.utils.Constants.Injection.HISTORY_ENDPOINT
import com.codingub.belarusianhistory.utils.Constants.Injection.IS_DEBUG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
        return AppRepositoryImpl(
            db.practiceAchievesDao, db.ticketAchievesDao
        )
    }

    @Provides
    @Singleton
    fun provideItemDatabase(app: App): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java, "history"
        ).createFromAsset("data/history")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideApp(): App {
        return App.getInstance()
    }

    /*
        Constants
     */

    @Provides
    @Named(IS_DEBUG)
    fun providesIsDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Named(BUILD_VERSION_CODE)
    fun providesBuildVersionCode() = BuildConfig.VERSION_CODE

    @Provides
    @Named(BUILD_VERSION_NAME)
    fun providesBuildVersionName() = BuildConfig.VERSION_NAME

    @Provides
    @Named(HISTORY_ENDPOINT)
    fun providesHistoryEndpoint() = BuildConfig.history_endpoint

    /*
        Networking
     */

    @Provides
    @Singleton
    fun provideHistoryAppService(networking: HistoryNetworking): HistoryAppApi =
        networking.historyAppApi()

}



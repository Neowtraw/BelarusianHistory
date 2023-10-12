package com.codingub.belarusianhistory.di

import com.codingub.belarusianhistory.utils.logger.HistoryLogger
import com.codingub.belarusianhistory.utils.logger.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindsLogger(bind: HistoryLogger) : Logger
}
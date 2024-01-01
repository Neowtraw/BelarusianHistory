package com.codingub.belarusianhistory.di

import com.codingub.belarusianhistory.data.local.datasource.ResultLocalDataSourceImpl
import com.codingub.belarusianhistory.data.remote.datasource.ResultRemoteDataSourceImpl
import com.codingub.belarusianhistory.domain.datasource.ResultLocalDataSource
import com.codingub.belarusianhistory.domain.datasource.ResultRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindResultRemoteDataSource(
        resultRemoteDataSource: ResultRemoteDataSourceImpl
    ) : ResultRemoteDataSource

    @Binds
    abstract fun bindResultLocalDataSource(
        resultLocalDataSource: ResultLocalDataSourceImpl
    ) : ResultLocalDataSource
}
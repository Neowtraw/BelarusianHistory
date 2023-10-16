package com.codingub.belarusianhistory.di

import com.codingub.belarusianhistory.data.repository.users.UserRepository
import com.codingub.belarusianhistory.data.repository.users.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun providesUserRepository(repository: UserRepositoryImpl): UserRepository = repository

}
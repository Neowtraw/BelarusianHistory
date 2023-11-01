package com.codingub.belarusianhistory.di

import com.codingub.belarusianhistory.data.repository.achieves.AchieveRepository
import com.codingub.belarusianhistory.data.repository.achieves.AchieveRepositoryImpl
import com.codingub.belarusianhistory.data.repository.practices.PracticeQuestionRepository
import com.codingub.belarusianhistory.data.repository.practices.PracticeQuestionRepositoryImpl
import com.codingub.belarusianhistory.data.repository.tickets.TicketQuestionRepository
import com.codingub.belarusianhistory.data.repository.tickets.TicketQuestionRepositoryImpl
import com.codingub.belarusianhistory.data.repository.tickets.TicketRepository
import com.codingub.belarusianhistory.data.repository.tickets.TicketRepositoryImpl
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

    @Provides
    @ViewModelScoped
    fun providesTicketRepository(repository: TicketRepositoryImpl) : TicketRepository = repository

    @Provides
    @ViewModelScoped
    fun providesTicketQuestionRepository(repository: TicketQuestionRepositoryImpl) : TicketQuestionRepository = repository

    @Provides
    @ViewModelScoped
    fun providesPracticeRepository(repository: PracticeQuestionRepositoryImpl) : PracticeQuestionRepository = repository

    @Provides
    @ViewModelScoped
    fun providesAchieveRepository(repository: AchieveRepositoryImpl) : AchieveRepository = repository

}
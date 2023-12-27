package com.codingub.belarusianhistory.di

import com.codingub.belarusianhistory.data.repos.AchieveRepository
import com.codingub.belarusianhistory.data.repos.AchieveRepositoryImpl
import com.codingub.belarusianhistory.data.repos.EventRepository
import com.codingub.belarusianhistory.data.repos.EventRepositoryImpl
import com.codingub.belarusianhistory.data.repos.GroupRepository
import com.codingub.belarusianhistory.data.repos.GroupRepositoryImpl
import com.codingub.belarusianhistory.data.repos.PracticeQuestionRepository
import com.codingub.belarusianhistory.data.repos.PracticeQuestionRepositoryImpl
import com.codingub.belarusianhistory.data.repos.TicketQuestionRepository
import com.codingub.belarusianhistory.data.repos.TicketQuestionRepositoryImpl
import com.codingub.belarusianhistory.data.repos.TicketRepository
import com.codingub.belarusianhistory.data.repos.TicketRepositoryImpl
import com.codingub.belarusianhistory.data.repos.UserRepository
import com.codingub.belarusianhistory.data.repos.UserRepositoryImpl
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

    @Provides
    @ViewModelScoped
    fun providesGroupRepository(repository: GroupRepositoryImpl) : GroupRepository = repository

    @Provides
    @ViewModelScoped
    fun providesEventRepository(repository: EventRepositoryImpl) : EventRepository = repository

}
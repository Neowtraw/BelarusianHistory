package com.codingub.belarusianhistory.di

import com.codingub.belarusianhistory.data.repos.AchieveRepositoryImpl
import com.codingub.belarusianhistory.data.repos.EventRepositoryImpl
import com.codingub.belarusianhistory.data.repos.GroupRepositoryImpl
import com.codingub.belarusianhistory.data.repos.MapRepositoryImpl
import com.codingub.belarusianhistory.data.repos.PracticeQuestionRepositoryImpl
import com.codingub.belarusianhistory.data.repos.TicketQuestionRepositoryImpl
import com.codingub.belarusianhistory.data.repos.TicketRepositoryImpl
import com.codingub.belarusianhistory.data.repos.UserRepositoryImpl
import com.codingub.belarusianhistory.domain.repos.AchieveRepository
import com.codingub.belarusianhistory.domain.repos.EventRepository
import com.codingub.belarusianhistory.domain.repos.GroupRepository
import com.codingub.belarusianhistory.domain.repos.MapRepository
import com.codingub.belarusianhistory.domain.repos.PracticeQuestionRepository
import com.codingub.belarusianhistory.domain.repos.TicketQuestionRepository
import com.codingub.belarusianhistory.domain.repos.TicketRepository
import com.codingub.belarusianhistory.domain.repos.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class ReposModule {

    @Binds
    abstract fun providesUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun providesTicketRepository(repository: TicketRepositoryImpl): TicketRepository

    @Binds
    abstract fun providesTicketQuestionRepository(repository: TicketQuestionRepositoryImpl): TicketQuestionRepository

    @Binds
    abstract fun providesPracticeRepository(repository: PracticeQuestionRepositoryImpl): PracticeQuestionRepository

    @Binds
    abstract fun providesAchieveRepository(repository: AchieveRepositoryImpl): AchieveRepository

    @Binds
    abstract fun providesGroupRepository(repository: GroupRepositoryImpl): GroupRepository

    @Binds
    abstract fun providesEventRepository(repository: EventRepositoryImpl): EventRepository

    @Binds
    abstract fun providesMapRepository(repository: MapRepositoryImpl): MapRepository

}
package com.example.marketsharesapp.domain.di

import com.example.marketsharesapp.domain.LoadDataUseCase
import com.example.marketsharesapp.domain.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

class DomainModule {
    @Module
    @InstallIn(SingletonComponent::class)
    object DomainModule {

        @Provides
        fun provideLoadDataUseCase(repository: NetworkRepository): LoadDataUseCase =
            LoadDataUseCase(repository)
    }
}
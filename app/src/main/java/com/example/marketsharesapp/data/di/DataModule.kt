package com.example.marketsharesapp.data.di

import com.example.marketsharesapp.data.NetworkRepositoryImpl
import com.example.marketsharesapp.data.mapper.CandleMapper
import com.example.marketsharesapp.data.network.ApiFactory
import com.example.marketsharesapp.data.network.ApiService
import com.example.marketsharesapp.domain.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideApiService(): ApiService = ApiFactory.apiService

    @Provides
    fun provideCandleMapper() = CandleMapper()

    @Provides
    fun provideNetworkRepository(
        apiService: ApiService,
        mapper: CandleMapper
    ): NetworkRepository = NetworkRepositoryImpl(apiService, mapper)
}
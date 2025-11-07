package com.example.marketsharesapp.data

import com.example.marketsharesapp.data.mapper.CandleMapper
import com.example.marketsharesapp.data.network.ApiFactory
import com.example.marketsharesapp.data.network.ApiService
import com.example.marketsharesapp.domain.NetworkRepository
import com.example.marketsharesapp.domain.entity.CandleAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkRepositoryImpl(
    private val apiService: ApiService,
    private val mapper: CandleMapper,
): NetworkRepository {

    private val _candlesFlow = MutableStateFlow<List<CandleAction>>(emptyList())
    override val candlesFlow = _candlesFlow.asStateFlow()

    override suspend fun loadData() {
        val data = apiService.getCandles().candles
        _candlesFlow.value = mapper.map(data)
    }
}
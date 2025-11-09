package com.example.marketsharesapp.data

import android.util.Log
import com.example.marketsharesapp.data.mapper.CandleMapper
import com.example.marketsharesapp.data.network.ApiFactory
import com.example.marketsharesapp.data.network.ApiService
import com.example.marketsharesapp.domain.NetworkRepository
import com.example.marketsharesapp.domain.entity.CandleAction
import com.example.marketsharesapp.presentation.TimeFrame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import retrofit2.http.Path

class NetworkRepositoryImpl(
    private val apiService: ApiService,
    private val mapper: CandleMapper,
): NetworkRepository {

    private val _candlesFlow = MutableStateFlow<List<CandleAction>>(emptyList())
    override val candlesFlow = _candlesFlow.asStateFlow()

    override suspend fun loadData(timeFrame: TimeFrame) {
        withContext(Dispatchers.IO){
            try {
                val response = apiService.getCandles(timeFrame.multiplier, timeFrame.timespan)
                Log.d("LOAD", "Received ${response.candles.size} candles")
                _candlesFlow.value = mapper.map(response.candles, timeFrame)
                Log.d("LOAD", "Mapper executed")
            } catch (e: Exception) {
                Log.e("LOAD", "Failed to load candles", e)
            }
        }
    }
}
package com.example.marketsharesapp.domain

import com.example.marketsharesapp.domain.entity.CandleAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NetworkRepository {
    val candlesFlow: StateFlow<List<CandleAction>>
    suspend fun loadData()
}
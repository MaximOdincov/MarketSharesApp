package com.example.marketsharesapp.domain

import com.example.marketsharesapp.domain.entity.CandleAction
import com.example.marketsharesapp.presentation.TimeFrame
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: NetworkRepository) {
    operator fun invoke(): Flow<List<CandleAction>> {
        return repository.candlesFlow
    }

    suspend fun loadData(timeFrame: TimeFrame){
        repository.loadData(timeFrame)
    }
}
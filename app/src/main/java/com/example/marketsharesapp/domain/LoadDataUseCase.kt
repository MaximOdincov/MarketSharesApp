package com.example.marketsharesapp.domain

import com.example.marketsharesapp.domain.entity.CandleAction

class LoadDataUseCase(private val repository: NetworkRepository) {
    suspend operator fun invoke(): List<CandleAction>{
        return repository.loadData()
    }
}
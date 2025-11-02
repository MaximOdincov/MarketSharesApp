package com.example.marketsharesapp.domain

import com.example.marketsharesapp.domain.entity.CandleAction

interface NetworkRepository {
    fun loadData(): List<CandleAction>
}
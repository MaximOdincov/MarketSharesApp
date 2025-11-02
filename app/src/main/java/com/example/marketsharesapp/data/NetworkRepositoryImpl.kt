package com.example.marketsharesapp.data

import com.example.marketsharesapp.data.mapper.CandleMapper
import com.example.marketsharesapp.data.network.ApiFactory
import com.example.marketsharesapp.data.network.ApiService
import com.example.marketsharesapp.domain.NetworkRepository
import com.example.marketsharesapp.domain.entity.CandleAction

class NetworkRepositoryImpl(
    private val apiService: ApiService,
    private val mapper: CandleMapper
): NetworkRepository {

    override fun loadData(): List<CandleAction> {
        TODO("Not yet implemented")
    }
}
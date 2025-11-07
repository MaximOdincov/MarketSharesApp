package com.example.marketsharesapp.data.network

import com.example.marketsharesapp.data.model.ResponseDto
import com.example.marketsharesapp.domain.entity.CandleAction
import retrofit2.http.GET

interface ApiService {
    @GET("aggs/ticker/AAPL/range/1/hour/2025-11-01/2025-11-05?adjusted=true&sort=asc&limit=50000&apiKey=cxBIch_W27qP5N8QhcsqQPzky1yHKi7o")
    suspend fun getCandles(): ResponseDto
}
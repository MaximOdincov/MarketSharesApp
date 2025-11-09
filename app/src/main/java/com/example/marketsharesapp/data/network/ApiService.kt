package com.example.marketsharesapp.data.network

import com.example.marketsharesapp.data.model.ResponseDto
import com.example.marketsharesapp.domain.entity.CandleAction
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("aggs/ticker/AAPL/range/{multiplier}/{timespan}/2025-01-09/2025-01-12")
    suspend fun getCandles(
        @Path("multiplier") multiplier: Int,
        @Path("timespan") timespan: String,
        @Query("adjusted") adjusted: Boolean = true,
        @Query("sort") sort: String = "desc",
        @Query("limit") limit: Int = 50000,
        @Query("apiKey") apiKey: String = "cxBIch_W27qP5N8QhcsqQPzky1yHKi7o"
    ): ResponseDto
}
package com.example.marketsharesapp.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

data class ResponseDto(
    @Json(name = "results") val candles: List<CandleActionDto>
)
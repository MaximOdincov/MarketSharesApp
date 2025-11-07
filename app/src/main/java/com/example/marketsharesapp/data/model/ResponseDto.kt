package com.example.marketsharesapp.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    @SerializedName("results") val candles: List<CandleActionDto>
)
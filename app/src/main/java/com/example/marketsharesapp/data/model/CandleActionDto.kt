package com.example.marketsharesapp.data.model

import android.icu.util.Calendar
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class CandleActionDto(
    @SerializedName("o") val  open: Float,
    @SerializedName("c") val  close: Float,
    @SerializedName("l") val  low: Float,
    @SerializedName("h") val  high: Float,
    @SerializedName("t") val  time: Long
)
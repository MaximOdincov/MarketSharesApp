package com.example.marketsharesapp.data.model

import android.icu.util.Calendar
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable
import java.util.Date

data class CandleActionDto(
    @Json(name = "o") val  open: Double,
    @Json(name = "c") val  close: Double,
    @Json(name = "l") val  low: Double,
    @Json(name = "h") val  high: Double,
    @Json(name = "t") val  time: Long
)
package com.example.marketsharesapp.domain.entity

import android.icu.util.Calendar
import androidx.compose.runtime.Immutable
import com.example.marketsharesapp.presentation.TimeFrame

@Immutable
data class CandleAction(
    val open: Float,
    val close: Float,
    val low: Float,
    val high: Float,
    val time: Calendar,
    val timeFrame: TimeFrame
)
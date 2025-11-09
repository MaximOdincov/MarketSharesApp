package com.example.marketsharesapp.presentation.main

import androidx.compose.material3.TimeInput
import com.example.marketsharesapp.domain.entity.CandleAction
import com.example.marketsharesapp.presentation.TimeFrame

sealed class MainScreenState {
    data object Initial : MainScreenState()
    data object Loading : MainScreenState()
    data class Content(
        val candles: List<CandleAction>,
        val timeFrame: TimeFrame = TimeFrame.MIN_30
    ) : MainScreenState()
}
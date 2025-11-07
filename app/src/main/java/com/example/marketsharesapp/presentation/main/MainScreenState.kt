package com.example.marketsharesapp.presentation.main

import com.example.marketsharesapp.domain.entity.CandleAction

sealed class MainScreenState {
    data object Initial : MainScreenState()
    data object Loading : MainScreenState()
    data class Content(val candles: List<CandleAction>) : MainScreenState()
}
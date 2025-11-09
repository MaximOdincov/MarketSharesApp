package com.example.marketsharesapp.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import com.example.marketsharesapp.domain.entity.CandleAction
import com.example.marketsharesapp.presentation.TimeFrame
import com.example.marketsharesapp.presentation.terminal.Terminal
import com.example.marketsharesapp.presentation.theme.MarketSharesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketSharesAppTheme {
                val state by viewModel.state.collectAsState()
                when (val s = state) {
                    is MainScreenState.Loading -> Loading()
                    is MainScreenState.Content -> Content(
                        candles = s.candles,
                        selectedFrame = s.timeFrame,
                        onTimeFrameSelected = { timeFrame ->
                            viewModel.loadData(timeFrame)
                        })
                    is MainScreenState.Initial -> {}
                }
            }
        }
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun Content(candles: List<CandleAction>, selectedFrame: TimeFrame, onTimeFrameSelected: (TimeFrame) -> Unit) {
    Terminal(
        actions = candles,
        selectedFrame = selectedFrame,
        onTimeFrameSelected = onTimeFrameSelected
    )
}


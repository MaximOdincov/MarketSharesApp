package com.example.marketsharesapp.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.marketsharesapp.domain.entity.CandleAction
import com.example.marketsharesapp.presentation.theme.MarketSharesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketSharesAppTheme {
                val state = viewModel.state.collectAsState()
                when (val s = state.value) {
                    is MainScreenState.Loading -> Loading()
                    is MainScreenState.Content -> Content(s.candles)
                    is MainScreenState.Initial -> {}
                }
            }
        }
    }
}

@Composable
fun Loading() {
    Log.d("my", "loading")
}

@Composable
fun Content(candles: List<CandleAction>) {
    Log.d("my", candles[0].toString())
}


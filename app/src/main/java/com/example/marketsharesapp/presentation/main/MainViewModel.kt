package com.example.marketsharesapp.presentation.main

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketsharesapp.domain.LoadDataUseCase
import com.example.marketsharesapp.domain.entity.CandleAction
import com.example.marketsharesapp.presentation.TimeFrame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loadDataUseCase: LoadDataUseCase): ViewModel(){
    private val initialTimeFrame = TimeFrame.MIN_15
    val candles: StateFlow<List<CandleAction>> = loadDataUseCase().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )
    private val _state = MutableStateFlow<MainScreenState>((MainScreenState.Initial))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            candles.collect { list ->
                    _state.value = if (list.isEmpty()) MainScreenState.Initial else MainScreenState.Content(candles = list, timeFrame = list[0].timeFrame)
                }
        }
            loadData(initialTimeFrame)
        }

    fun loadData(timeFrame: TimeFrame){
        viewModelScope.launch{
            _state.value = MainScreenState.Loading
            withContext(Dispatchers.IO){
                loadDataUseCase.loadData(timeFrame)
            }

        }
    }
}
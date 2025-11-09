package com.example.marketsharesapp.presentation.terminal

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.marketsharesapp.domain.entity.CandleAction
import kotlinx.serialization.Serializable
import java.nio.file.Files.size
import kotlin.math.roundToInt

data class TerminalState(
    var candleList: List<CandleAction>,
    var visibleCandleCount: Int = 100,
    var scrolledBy: Float = 0f,
    var sizeWidth: Float = 0f
) {
    val candleWidth: Float
        get() = sizeWidth/visibleCandleCount

    val visibleCandles: List<CandleAction>
        get() {
            val startIndex = (scrolledBy/candleWidth).roundToInt().coerceAtLeast(0)
            val endIndex = (startIndex + visibleCandleCount).coerceAtMost(candleList.size)
            return candleList.subList(startIndex, endIndex)
        }
}

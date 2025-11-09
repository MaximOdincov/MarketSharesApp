package com.example.marketsharesapp.presentation.terminal

import com.example.marketsharesapp.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marketsharesapp.domain.entity.CandleAction
import com.example.marketsharesapp.presentation.TimeFrame
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Locale
import kotlin.collections.List
import kotlin.math.roundToInt

private const val MIN_VISIBLE_CANDLE_COUNT = 15
@Composable fun Terminal(actions: List<CandleAction>, onTimeFrameSelected: (TimeFrame) -> Unit, selectedFrame: TimeFrame){

    val terminalSaver: Saver<MutableState<TerminalState>, Any> = listSaver(
        save = {
            listOf(
                it.value.candleList,
                it.value.sizeWidth,
                it.value.scrolledBy,
                it.value.visibleCandleCount
            )
        },
        restore = {
            mutableStateOf(
                TerminalState(
                    candleList = it[0] as List<CandleAction>,
                    sizeWidth = it[1] as Float,
                    visibleCandleCount = it[2] as Int,
                    scrolledBy = it[3] as Float
                )
            )
        }
    )

    var terminalState by rememberSaveable(saver = terminalSaver) {
        mutableStateOf(TerminalState(actions))
    }

    val transformableState = rememberTransformableState {zoomChange, panChange, _ ->
        val visibleCandleCount = (terminalState.visibleCandleCount / zoomChange).roundToInt().coerceIn(MIN_VISIBLE_CANDLE_COUNT, actions.size)

        val scrolledBy = (terminalState.scrolledBy + panChange.x).coerceIn(0f, (actions.size - visibleCandleCount) * terminalState.candleWidth)

        terminalState = terminalState.copy(
            visibleCandleCount = visibleCandleCount,
            scrolledBy = scrolledBy
        )
    }

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black)
            .padding(36.dp)
            .transformable(transformableState)
            .onSizeChanged{
                terminalState = terminalState.copy(sizeWidth = it.width.toFloat())
            }
    ){

        val max = terminalState.visibleCandles.maxOf{it.high}
        val min = terminalState.visibleCandles.minOf{it.low}
        val pxPerPoint = size.height/(max-min)

        translate(left = terminalState.scrolledBy){
            actions.forEachIndexed {index, action ->
                val offsetX = size.width - index * terminalState.candleWidth

                drawTimeDelimiter(
                    candle = action,
                    nextCandle = if (index < terminalState.candleList.size - 1) {
                        terminalState.candleList[index + 1]
                    } else {
                        null
                    },
                    timeFrame = selectedFrame,
                    offsetX = offsetX,
                    textMeasurer = textMeasurer
                )

                drawLine(
                    color = Color.White,
                    start = Offset(offsetX, size.height - ((action.low - min) * pxPerPoint)),
                    Offset(offsetX, size.height - (action.high - min) * pxPerPoint),
                    strokeWidth = 1f
                )

                drawLine(
                    color = if(action.close > action.open) Color.Green else Color.Red,
                    start = Offset(offsetX, size.height - ((action.open - min) * pxPerPoint)),
                    Offset(offsetX, size.height - (action.close - min) * pxPerPoint),
                    strokeWidth = terminalState.candleWidth/2
                )
            }
        }

        actions.firstOrNull()?.let{
            drawPrices(
                maxPrice = max,
                minPrice = min,
                lastPrice = it.close,
                pxPerPoint = pxPerPoint,
                textMeasurer = textMeasurer
            )
        }
    }

    TimeFrames(
        selectedFrame = selectedFrame,
        onTimeFrameSelected = onTimeFrameSelected)
}

private fun DrawScope.drawPrices(
    maxPrice: Float,
    minPrice: Float,
    lastPrice: Float,
    pxPerPoint: Float,
    textMeasurer: TextMeasurer
){
    //max price
    drawDashedLine(
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f))
    drawTextPrice(
        textMeasurer = textMeasurer,
        price = maxPrice,
        offsetY = 0f
    )

    //last price
    val lastPriceOffsetY =  size.height - ((lastPrice-minPrice) * pxPerPoint)
    drawDashedLine(
        start = Offset(0f, lastPriceOffsetY),
        end = Offset(size.width, lastPriceOffsetY)
    )
    drawTextPrice(
        textMeasurer = textMeasurer,
        price = lastPrice,
        offsetY = lastPriceOffsetY
    )

    //min price
    drawDashedLine(
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height)
    )
    drawTextPrice(
        textMeasurer = textMeasurer,
        price = minPrice,
        offsetY = size.height
    )
}

private fun DrawScope.drawDashedLine(
    color: Color = Color.White,
    start: Offset,
    end: Offset,
    strokeWith: Float = 1f
){
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = strokeWith,
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(
                4.dp.toPx(), 4.dp.toPx()
            )
        )
    )
}

private fun DrawScope.drawTextPrice(
    textMeasurer: TextMeasurer,
    price: Float,
    offsetY: Float
){
    val textLayoutResult = textMeasurer.measure(
        text = price.toString(),
        style = TextStyle(
            color = Color.White,
            fontSize = 14.sp
        )
    )
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(size.width - textLayoutResult.size.width, offsetY)
    )
}

@Composable
private fun TimeFrames(
    selectedFrame: TimeFrame,
    onTimeFrameSelected: (TimeFrame) -> Unit
){
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
         TimeFrame.entries.forEach{timeFrame ->
            val labelResId = when(timeFrame){
                TimeFrame.MIN_5 -> R.string.timeframe_5_minutes
                TimeFrame.MIN_15 -> R.string.timeframe_15_minutes
                TimeFrame.MIN_30 -> R.string.timeframe_30_minutes
                TimeFrame.HOUR_1 -> R.string.timeframe_1_hour
            }
             val isSelected = timeFrame == selectedFrame
             AssistChip(
                 onClick = {onTimeFrameSelected(timeFrame)},
                 label = { Text(text = stringResource(id = labelResId)) },
                 colors = AssistChipDefaults.assistChipColors(
                     containerColor = if(isSelected) Color.White else Color.Black,
                     labelColor = if(isSelected) Color.Black else Color.White
                 )
             )

         }
    }
}

private fun DrawScope.drawTimeDelimiter(
    candle: CandleAction,
    nextCandle: CandleAction?,
    timeFrame: TimeFrame,
    offsetX: Float,
    textMeasurer: TextMeasurer
) {
    val calendar = candle.time

    val minutes = calendar.get(Calendar.MINUTE)
    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val shouldDrawDelimiter = when (timeFrame) {
        TimeFrame.MIN_5 -> {
            minutes == 0
        }

        TimeFrame.MIN_15 -> {
            minutes == 0 && hours % 2 == 0
        }

        TimeFrame.MIN_30, TimeFrame.HOUR_1 -> {
            val nextBarDay = nextCandle?.time?.get(Calendar.DAY_OF_MONTH)
            day != nextBarDay
        }
    }
    if (!shouldDrawDelimiter) return

    drawLine(
        color = Color.White.copy(alpha = 0.5f),
        start = Offset(offsetX, 0f),
        end = Offset(offsetX, size.height),
        strokeWidth = 1f,
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(4.dp.toPx(), 4.dp.toPx())
        )
    )

    val numOfMonth = calendar.get(Calendar.MONTH)
    val nameOfMonth = DateFormatSymbols(Locale.getDefault()).shortMonths[numOfMonth]
    val text = when (timeFrame) {
        TimeFrame.MIN_5, TimeFrame.MIN_15 -> {
            String.format("%02d:00", hours)
        }

        TimeFrame.MIN_30, TimeFrame.HOUR_1 -> {
            String.format("%s %s", day, nameOfMonth)
        }
    }
    val textLayoutResult = textMeasurer.measure(
        text = text,
        style = TextStyle(
            color = Color.White,
            fontSize = 12.sp
        )
    )
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(offsetX - textLayoutResult.size.width / 2, size.height)
    )
}
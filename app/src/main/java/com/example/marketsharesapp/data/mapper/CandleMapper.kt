package com.example.marketsharesapp.data.mapper

import android.icu.util.Calendar
import com.example.marketsharesapp.data.model.CandleActionDto
import com.example.marketsharesapp.domain.entity.CandleAction
import com.example.marketsharesapp.presentation.TimeFrame
import java.util.Date

class CandleMapper {
    fun map(candleActions: List<CandleActionDto>, timeFrame: TimeFrame): List<CandleAction> {
        val actions = mutableListOf<CandleAction>()
        candleActions.forEach { candleAction ->
            actions.add(
                CandleAction(
                    open = candleAction.open.toFloat(),
                    close = candleAction.close.toFloat(),
                    high = candleAction.high.toFloat(),
                    low = candleAction.low.toFloat(),
                    time = Calendar.getInstance().apply {
                        time = Date(candleAction.time)
                    },
                    timeFrame = timeFrame
                )
            )
        }
        return actions
    }
}
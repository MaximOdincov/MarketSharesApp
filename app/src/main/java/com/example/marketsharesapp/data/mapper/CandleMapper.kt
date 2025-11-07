package com.example.marketsharesapp.data.mapper

import android.icu.util.Calendar
import com.example.marketsharesapp.data.model.CandleActionDto
import com.example.marketsharesapp.domain.entity.CandleAction
import java.util.Date

class CandleMapper {
    fun map(candleActions: List<CandleActionDto>): List<CandleAction> {
        val actions = mutableListOf<CandleAction>()
        candleActions.forEach { candleAction ->
            actions.add(
                CandleAction(
                    open = candleAction.open,
                    close = candleAction.close,
                    high = candleAction.high,
                    low = candleAction.low,
                    time = Calendar.getInstance().apply {
                        time = Date(candleAction.time)
                    }
                )
            )
        }
        return actions
    }
}
package com.exchange.rate.data.repository

import com.exchange.rate.entity.ActionResult
import com.exchange.rate.entity.remote.CurrencyDto
import kotlinx.coroutines.flow.Flow

interface CurrencyRepo {
  fun getBaseCurrencies(): Flow<List<String>>

  suspend fun getCurrencies(selectedCurrency: String): ActionResult<CurrencyDto>
}
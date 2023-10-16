package com.exchange.rate.data.repository

import com.exchange.rate.data.remote.CurrencyService
import com.exchange.rate.data.util.makeApiCall
import com.exchange.rate.entity.ActionResult
import com.exchange.rate.entity.remote.CurrencyDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CurrencyRepoImpl @Inject constructor(private val currencyService: CurrencyService) :
  CurrencyRepo {

  override fun getBaseCurrencies(): Flow<List<String>> = flowOf(
    listOf(
      "EUR",
      "USD",
      "JPY"
    )
  )

  override suspend fun getCurrencies(selectedCurrency: String): ActionResult<CurrencyDto> =
    makeApiCall {
      currencyService.getCurrency(baseCurrency = selectedCurrency)
    }
}
package com.exchange.rate.entity.local

data class CurrencyModel(
  val ratesModel: RatesModel,
  val success: Boolean = false,
)
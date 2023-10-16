package com.exchange.rate.entity

import com.exchange.rate.entity.local.CurrencyModel
import com.exchange.rate.entity.local.RatesModel
import com.exchange.rate.entity.remote.CurrencyDto
import com.exchange.rate.entity.remote.RatesDto

fun CurrencyDto.toCurrencyEntity() = CurrencyModel(
  success = success ?: false,
  ratesModel = ratesDto?.toRatesEntity() ?: RatesModel()
)

fun RatesDto.toRatesEntity() = RatesModel(
  aED = aED ?: 0.0,
  uSD = uSD ?: 0.0,
  bYN = bYN ?: 0.0,
  rUB = rUB ?: 0.0
)
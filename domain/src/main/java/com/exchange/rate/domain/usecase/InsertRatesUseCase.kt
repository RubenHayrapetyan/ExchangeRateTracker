package com.exchange.rate.domain.usecase

import com.exchange.rate.entity.local.RateEntity

interface InsertRatesUseCase {
  suspend operator fun invoke(rates: List<RateEntity>)
}
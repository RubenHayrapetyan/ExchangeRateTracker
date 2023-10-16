package com.exchange.rate.domain.usecase

import com.exchange.rate.entity.ActionResult
import com.exchange.rate.entity.local.RateEntity

interface GetCurrenciesUseCase {
  suspend operator fun invoke(selectedCurrency: String): ActionResult<List<RateEntity>>
}
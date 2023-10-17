package com.exchange.rate.domain.usecase

import com.exchange.rate.data.repository.RateRepo
import com.exchange.rate.entity.local.RateEntity
import javax.inject.Inject

class InsertRatesUseCaseImpl @Inject constructor(private val rateRepo: RateRepo) : InsertRatesUseCase {

  override suspend fun invoke(rates: List<RateEntity>) {
    rateRepo.insertRates(rates = rates)
  }
}
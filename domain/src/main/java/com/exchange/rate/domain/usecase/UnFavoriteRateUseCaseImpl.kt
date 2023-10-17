package com.exchange.rate.domain.usecase

import com.exchange.rate.data.repository.RateRepo
import javax.inject.Inject

class UnFavoriteRateUseCaseImpl @Inject constructor(private val rateRepo: RateRepo) :
  UnFavoriteRateUseCase {

  override suspend fun invoke(rateName: String) {
    rateRepo.unFavoriteRate(rateName = rateName)
  }
}
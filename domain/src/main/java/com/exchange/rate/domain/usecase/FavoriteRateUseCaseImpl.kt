package com.exchange.rate.domain.usecase

import com.exchange.rate.data.repository.RateRepo
import javax.inject.Inject

class FavoriteRateUseCaseImpl @Inject constructor(private val rateRepo: RateRepo): FavoriteRateUseCase {
  override suspend fun invoke(rateName: String, baseRateName: String) {
    rateRepo.favoriteRate(rateName = rateName, baseRateName = baseRateName)
  }
}
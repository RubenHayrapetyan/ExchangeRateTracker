package com.exchange.rate.domain.usecase

interface FavoriteRateUseCase {
  suspend operator fun invoke(rateName: String, baseRateName: String)
}
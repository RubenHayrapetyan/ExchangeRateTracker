package com.exchange.rate.domain.usecase

interface UnFavoriteRateUseCase {
  suspend operator fun invoke(rateName: String)
}
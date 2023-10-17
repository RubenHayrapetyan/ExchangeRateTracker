package com.exchange.rate.domain.usecase

import com.exchange.rate.entity.local.RateEntity
import kotlinx.coroutines.flow.Flow

interface GetAllFavoriteRatesUseCase {
  operator fun invoke(): Flow<List<RateEntity>>
}
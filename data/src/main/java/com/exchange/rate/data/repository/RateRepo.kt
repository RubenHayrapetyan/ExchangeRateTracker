package com.exchange.rate.data.repository

import com.exchange.rate.entity.local.RateEntity
import kotlinx.coroutines.flow.Flow

interface RateRepo {
  suspend fun favoriteRate(rateName: String, baseRateName: String)

  suspend fun unFavoriteRate(rateName: String)

  fun getAllFavoriteRates(): Flow<List<RateEntity>>
}
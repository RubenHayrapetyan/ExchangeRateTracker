package com.exchange.rate.data.repository

import com.exchange.rate.data.local.RateDao
import com.exchange.rate.entity.local.RateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RateRepoImpl @Inject constructor(private val rateDao: RateDao): RateRepo {
  override suspend fun favoriteRate(rateName: String, baseRateName: String) {
    rateDao.favoriteRate(rateName = rateName, baseRateName = baseRateName)
  }

  override suspend fun unFavoriteRate(rateName: String) {
    rateDao.unFavoriteRate(rateName = rateName)
  }

  override fun getAllFavoriteRates(): Flow<List<RateEntity>> =
    rateDao.getAllFavoriteRates()
}
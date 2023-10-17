package com.exchange.rate.domain.usecase

import com.exchange.rate.data.repository.RateRepo
import com.exchange.rate.entity.local.RateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteRatesUseCaseImpl @Inject constructor(private val rateRepo: RateRepo): GetAllFavoriteRatesUseCase {

  override fun invoke(): Flow<List<RateEntity>> =
    rateRepo.getAllFavoriteRates()
}
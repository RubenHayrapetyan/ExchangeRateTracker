package com.exchange.rate.domain.usecase

import com.exchange.rate.data.repository.CurrencyRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBaseCurrenciesUseCaseImpl @Inject constructor(private val currencyRepo: CurrencyRepo) :
  GetBaseCurrenciesUseCase {

  override fun invoke(): Flow<List<String>> = currencyRepo.getBaseCurrencies()
}
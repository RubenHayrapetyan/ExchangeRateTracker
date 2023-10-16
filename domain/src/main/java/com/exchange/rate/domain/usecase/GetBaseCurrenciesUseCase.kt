package com.exchange.rate.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetBaseCurrenciesUseCase {

  operator fun invoke(): Flow<List<String>>
}
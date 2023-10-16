package com.exchange.rate.domain.di

import com.exchange.rate.domain.usecase.GetBaseCurrenciesUseCase
import com.exchange.rate.domain.usecase.GetBaseCurrenciesUseCaseImpl
import com.exchange.rate.domain.usecase.GetCurrenciesUseCase
import com.exchange.rate.domain.usecase.GetCurrenciesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UseCaseModule {
  @Binds
  abstract fun bindBaseCurrenciesUseCase(useCase: GetBaseCurrenciesUseCaseImpl): GetBaseCurrenciesUseCase

  @Binds
  abstract fun bindGetCurrenciesUseCase(useCase: GetCurrenciesUseCaseImpl): GetCurrenciesUseCase
}
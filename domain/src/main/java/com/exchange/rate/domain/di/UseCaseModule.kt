package com.exchange.rate.domain.di

import com.exchange.rate.domain.usecase.FavoriteRateUseCase
import com.exchange.rate.domain.usecase.FavoriteRateUseCaseImpl
import com.exchange.rate.domain.usecase.GetAllFavoriteRatesUseCase
import com.exchange.rate.domain.usecase.GetAllFavoriteRatesUseCaseImpl
import com.exchange.rate.domain.usecase.GetBaseCurrenciesUseCase
import com.exchange.rate.domain.usecase.GetBaseCurrenciesUseCaseImpl
import com.exchange.rate.domain.usecase.GetCurrenciesUseCase
import com.exchange.rate.domain.usecase.GetCurrenciesUseCaseImpl
import com.exchange.rate.domain.usecase.InsertRatesUseCase
import com.exchange.rate.domain.usecase.InsertRatesUseCaseImpl
import com.exchange.rate.domain.usecase.UnFavoriteRateUseCase
import com.exchange.rate.domain.usecase.UnFavoriteRateUseCaseImpl
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

  @Binds
  abstract fun bindFavoriteRateUseCase(useCase: FavoriteRateUseCaseImpl): FavoriteRateUseCase

  @Binds
  abstract fun bindGetAllFavoriteRateUseCase(useCase: GetAllFavoriteRatesUseCaseImpl): GetAllFavoriteRatesUseCase

  @Binds
  abstract fun bindInsertRatesUseCase(useCase: InsertRatesUseCaseImpl): InsertRatesUseCase

  @Binds
  abstract fun bindUnFavoriteRatesUseCase(useCase: UnFavoriteRateUseCaseImpl): UnFavoriteRateUseCase
}
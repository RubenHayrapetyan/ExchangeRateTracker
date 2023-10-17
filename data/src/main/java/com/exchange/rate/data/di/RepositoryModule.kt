package com.exchange.rate.data.di

import com.exchange.rate.data.repository.CurrencyRepo
import com.exchange.rate.data.repository.CurrencyRepoImpl
import com.exchange.rate.data.repository.RateRepo
import com.exchange.rate.data.repository.RateRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {
  @Binds
  abstract fun bindCurrencyRepository(repository: CurrencyRepoImpl): CurrencyRepo

  @Binds
  abstract fun bindRateRepository(repository: RateRepoImpl): RateRepo
}
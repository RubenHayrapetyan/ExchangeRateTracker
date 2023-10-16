package com.exchange.rate.data.di

import com.exchange.rate.data.repository.CurrencyRepo
import com.exchange.rate.data.repository.CurrencyRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {
  @Binds
  abstract fun bindCurrencyRepository(repository: CurrencyRepoImpl): CurrencyRepo
}
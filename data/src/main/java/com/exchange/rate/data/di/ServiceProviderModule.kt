package com.exchange.rate.data.di

import com.exchange.rate.data.util.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object ServiceProviderModule {
  @IODispatcher
  @Provides
  fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
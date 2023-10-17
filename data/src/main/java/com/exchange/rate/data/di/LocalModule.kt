package com.exchange.rate.data.di

import android.content.Context
import androidx.room.Room
import com.exchange.rate.constants.Constants
import com.exchange.rate.data.local.RateDao
import com.exchange.rate.data.local.ExchangeRateTrackerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object LocalModule {

  @Provides
  fun provideDatabase(@ApplicationContext context: Context): ExchangeRateTrackerDatabase {
    return Room.databaseBuilder(context, ExchangeRateTrackerDatabase::class.java, Constants.DB_NAME)
      .fallbackToDestructiveMigration()
      .build()
  }

  @Provides
  fun provideCompanyDao(db: ExchangeRateTrackerDatabase): RateDao = db.getRateDao()
}
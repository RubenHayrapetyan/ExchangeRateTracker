package com.exchange.rate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exchange.rate.entity.local.RateEntity

@Database(
  entities = [RateEntity::class],
  version = 1,
)
abstract class ExchangeRateTrackerDatabase : RoomDatabase() {
  abstract fun getRateDao(): RateDao
}
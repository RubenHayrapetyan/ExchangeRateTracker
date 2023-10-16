//package com.exchange.rate.data.local
//
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import com.exchange.rate.data.model.CurrencyDataModel
//
//@Database(
//  entities = [CurrencyDataModel::class],
//  version = 1,
//)
////@TypeConverters(ListStringConverter::class, ListIntConverter::class) TODO
//abstract class ExchangeRateTrackerDatabase : RoomDatabase() {
//  abstract fun getCurrencyDao(): CurrencyDao
//}
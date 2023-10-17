package com.exchange.rate.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exchange.rate.entity.local.RateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertRates(vararg rates: RateEntity)

  @Query("UPDATE RateEntity SET isFavorite = 1, baseRateName = :baseRateName WHERE rateName = :rateName")
  suspend fun favoriteRate(rateName: String, baseRateName: String)

  @Query("UPDATE RateEntity SET isFavorite = 0 WHERE rateName = :rateName")
  suspend fun unFavoriteRate(rateName: String)

  @Query("SELECT * FROM RateEntity WHERE isFavorite = 1")
  fun getAllFavoriteRates(): Flow<List<RateEntity>>
}
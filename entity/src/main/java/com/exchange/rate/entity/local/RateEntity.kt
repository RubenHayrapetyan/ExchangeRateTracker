package com.exchange.rate.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RateEntity(
  @PrimaryKey
  val rateName: String,
  val rateValue: Double,
  var isFavorite: Boolean,
  val baseRateName: String = ""
)

package com.exchange.rate.entity.local

data class RateEntity(
  val rateName: String,
  val rateValue: Double,
  var isFavorite: Boolean
)

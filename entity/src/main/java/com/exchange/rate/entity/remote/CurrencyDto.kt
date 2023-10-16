package com.exchange.rate.entity.remote

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
  @SerializedName("base")
  val base: String?,
  @SerializedName("date")
  val date: String?,
  @SerializedName("rates")
  val ratesDto: RatesDto?,
  @SerializedName("success")
  val success: Boolean? = null,
  @SerializedName("timestamp")
  val timestamp: Int? = null
)
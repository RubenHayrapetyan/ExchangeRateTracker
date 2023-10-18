package com.exchange.rate.entity.remote

import com.google.gson.annotations.SerializedName

data class ErrorDto(
  @SerializedName("error")
  val errorDetailsDto: ErrorDetailsDto?
)

data class ErrorDetailsDto(
  @SerializedName("code")
  val code: String?,
  @SerializedName("message")
  val message: String?
)
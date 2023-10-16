package com.exchange.rate.data.remote

import com.exchange.rate.constants.Request
import com.exchange.rate.entity.remote.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

  @GET(Request.BASE_URL + Request.LATEST_PATH + Request.ACCESS_KEY_PATH + Request.ACCESS_KEY)
  suspend fun getCurrency(
    @Query("base") baseCurrency: String,
  ): CurrencyDto
}
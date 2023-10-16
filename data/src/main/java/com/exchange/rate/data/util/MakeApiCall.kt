package com.exchange.rate.data.util

import com.exchange.rate.entity.ActionResult

suspend fun <R> makeApiCall(call: suspend () -> R) = try {
  ActionResult.Success(data = call())
} catch (e: Exception) {
  ActionResult.Error(e = e)
}
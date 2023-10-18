package com.exchange.rate.entity

sealed class ActionResult<out T> {
  data class Success<out T>(val data: T) : ActionResult<T>()
  data class Error(
    val errorCode: String?,
    val errorMessage: String?,
    val e: Throwable? = null
  ) : ActionResult<Nothing>()
}
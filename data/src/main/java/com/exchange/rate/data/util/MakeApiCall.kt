package com.exchange.rate.data.util

import com.exchange.rate.entity.ActionResult
import com.exchange.rate.entity.remote.ErrorDto
import com.google.gson.Gson
import retrofit2.HttpException

suspend fun <R> makeApiCall(call: suspend () -> R): ActionResult<R> {
  return try {
    val result = call()
    ActionResult.Success(result)
  } catch (e: Exception) {
    when (e) {
      is HttpException -> {
        val errorResponse = e.response()?.errorBody()
        if (errorResponse != null) {
          val gson = Gson()
          val errorJson = gson.fromJson(errorResponse.string(), ErrorDto::class.java)

          ActionResult.Error(errorJson.errorDetailsDto?.code, errorJson.errorDetailsDto?.message, e)
        } else {
          ActionResult.Error("Unknown", "Server error", e)
        }
      }

      else -> {
        ActionResult.Error("Unknown", e.message ?: "Unknown error", e)
      }
    }
  }
}

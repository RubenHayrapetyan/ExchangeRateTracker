package com.exchange.rate.tracker

data class CurrencyEntity( // TODO delete
  val id: Int,
  val currencyName: String,
  val currencyValue: Double,
  val isFavorite: Boolean
)

data class DropdownItem(val id: Int, val label: String) // TODO delete

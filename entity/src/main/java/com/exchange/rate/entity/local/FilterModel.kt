package com.exchange.rate.entity.local

import com.exchange.rate.entity.FilterType

data class FilterModel(
  val filterName: String,
  val filterType: FilterType
)
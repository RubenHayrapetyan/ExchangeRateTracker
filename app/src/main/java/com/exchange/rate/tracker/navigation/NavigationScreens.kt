package com.exchange.rate.tracker.navigation

import com.exchange.rate.tracker.util.Constants

sealed class Screen(val route: String){
  object FiltersScreen: Screen(route = Constants.FILTERS_ROUTE)
}
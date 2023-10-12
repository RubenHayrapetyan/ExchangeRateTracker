package com.exchange.rate.tracker.navigation

import com.exchange.rate.tracker.R
import com.exchange.rate.tracker.util.Constants

sealed class BottomBarScreen(val route: String, val title: Int, val icon: Int) {
  object Currencies : BottomBarScreen(
    route = Constants.CURRENCIES_ROUTE,
    title = R.string.title_currencies,
    icon = R.drawable.ic_currencies
  )

  object Favorites : BottomBarScreen(
    route = Constants.FAVORITES_ROUTE,
    title = R.string.title_favorites,
    icon = R.drawable.ic_favorites_on
  )
}
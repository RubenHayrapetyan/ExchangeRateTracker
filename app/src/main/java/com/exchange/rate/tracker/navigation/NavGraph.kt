package com.exchange.rate.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.exchange.rate.constants.Constants
import com.exchange.rate.entity.FilterType
import com.exchange.rate.tracker.ui.currencies.CurrenciesScreen
import com.exchange.rate.tracker.ui.favorites.FavoritesScreen
import com.exchange.rate.tracker.ui.filters.FiltersScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
  NavHost(navHostController, startDestination = BottomBarScreen.Currencies.route) {
    composable(
      route = BottomBarScreen.Currencies.route,
    ) {
      val filterType =
        it.savedStateHandle.get<Int>(Constants.ARG_FILTER) ?: FilterType.FROM_A_TO_Z.ordinal
      CurrenciesScreen(navHostController = navHostController, filterTypeOrdinal = filterType)
    }
    composable(BottomBarScreen.Favorites.route) {
      FavoritesScreen()
    }
    composable("${Screen.FiltersScreen.route}/{${Constants.ARG_FILTER}}") {
      val filterTypeOrdinal: Int =
        it.arguments?.getString(Constants.ARG_FILTER)?.toInt() ?: FilterType.FROM_A_TO_Z.ordinal
      FiltersScreen(
        navHostController = navHostController,
        selectedFilterTypeOrdinal = filterTypeOrdinal
      )
    }
  }
}
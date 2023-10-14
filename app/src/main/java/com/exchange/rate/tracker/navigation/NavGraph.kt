package com.exchange.rate.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.exchange.rate.tracker.ui.currencies.CurrenciesScreen
import com.exchange.rate.tracker.ui.favorites.FavoritesScreen
import com.exchange.rate.tracker.ui.filters.FiltersScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
  NavHost(navHostController, startDestination = BottomBarScreen.Currencies.route) {
    composable(BottomBarScreen.Currencies.route) {
      CurrenciesScreen(navHostController = navHostController)
    }
    composable(BottomBarScreen.Favorites.route) {
      FavoritesScreen()
    }
    composable(Screen.FiltersScreen.route) {
      FiltersScreen(navHostController = navHostController)
    }
  }
}
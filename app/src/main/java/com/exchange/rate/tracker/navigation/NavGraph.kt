package com.exchange.rate.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.exchange.rate.tracker.ui.currencies.CurrenciesScreen
import com.exchange.rate.tracker.ui.favorites.FavoritesScreen

@Composable
fun NavGraph(navController: NavHostController) {
  NavHost(navController, startDestination = BottomBarScreen.Currencies.route) {
    composable(BottomBarScreen.Currencies.route) {
      CurrenciesScreen()
    }
    composable(BottomBarScreen.Favorites.route) {
      FavoritesScreen()
    }
  }
}
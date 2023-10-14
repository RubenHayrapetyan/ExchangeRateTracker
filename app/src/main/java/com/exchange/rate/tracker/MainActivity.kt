package com.exchange.rate.tracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.exchange.rate.tracker.navigation.NavGraph
import com.exchange.rate.tracker.ui.BottomBar
import com.exchange.rate.tracker.ui.theme.ExchangeRateTrackerTheme
import com.exchange.rate.tracker.util.Constants

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ExchangeRateTrackerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          MainScreenView()
        }
      }
    }
  }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenView() {
  val navController = rememberNavController()
  var showBottomBar by rememberSaveable { mutableStateOf(true) }
  var topBarTitle by remember { mutableStateOf("") }
  var topBarUnderLine by remember { mutableStateOf(true) }
  val navBackStackEntry by navController.currentBackStackEntryAsState()

  when (navBackStackEntry?.destination?.route) {
    Constants.FILTERS_ROUTE -> {
      topBarTitle = stringResource(id = R.string.title_filters)
      showBottomBar = false
    }
    Constants.CURRENCIES_ROUTE -> {
      topBarTitle = stringResource(id = R.string.title_currencies)
      showBottomBar = true
    }
    Constants.FAVORITES_ROUTE -> {
      topBarTitle = stringResource(id = R.string.title_favorites)
      showBottomBar = true
    }
    else -> {
      showBottomBar = true // in all other cases show bottom bar
    }
  }
  
  Scaffold(
//    topBar = {
//      TopBar(navHostController = navController, title = stringResource(id = R.string.title_filters))
//    },
    bottomBar = {
      if (showBottomBar) {
        BottomBar(navController = navController)
      }
    }
  ) {
    Box(modifier = Modifier
      .fillMaxSize()
      .padding(it)) {
      NavGraph(navHostController = navController)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  ExchangeRateTrackerTheme {

  }
}
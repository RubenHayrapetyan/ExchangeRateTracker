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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.exchange.rate.tracker.navigation.NavGraph
import com.exchange.rate.tracker.ui.BottomBar
import com.exchange.rate.tracker.ui.theme.ExchangeRateTrackerTheme

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
private fun MainScreenView(){
  val navController = rememberNavController()
  Scaffold(
    bottomBar = { BottomBar(navController = navController) }
  ) {
    Box(modifier = Modifier.fillMaxSize().padding(it)) {
      NavGraph(navController = navController)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  ExchangeRateTrackerTheme {

  }
}
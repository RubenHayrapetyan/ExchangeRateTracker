package com.exchange.rate.tracker.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.exchange.rate.tracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
  modifier: Modifier = Modifier,
  navHostController: NavHostController,
  title: String,
  isBackEnabled: Boolean
) {
  TopAppBar(
    modifier = modifier,
    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.header)),
    navigationIcon = {
      if (isBackEnabled) {
        IconButton(
          onClick = {
            navHostController.popBackStack()
          }
        ) {
          Icon(
            tint = colorResource(id = R.color.primary),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back"
          )
        }
      }
    },
    title = {
      Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight(700)
      )
    }
  )
}
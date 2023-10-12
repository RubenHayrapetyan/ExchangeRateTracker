package com.exchange.rate.tracker.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.exchange.rate.tracker.R
import com.exchange.rate.tracker.navigation.BottomBarScreen

@Composable
fun BottomBar(navController: NavHostController) {
  val screens = listOf(
    BottomBarScreen.Currencies,
    BottomBarScreen.Favorites,
  )

  val navStackBackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = navStackBackEntry?.destination
  val outlineColor = colorResource(id = R.color.outline)

  Column {
    Canvas(
      modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
    ) {
      drawLine(
        color = outlineColor,
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f),
      )
    }

    Row(
      modifier = Modifier
        .background(color = Transparent)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      screens.forEach { screen ->
        BottomBarItem(
          screen = screen,
          currentDestination = currentDestination,
          navController = navController
        )
      }
    }
  }
}

@Composable
private fun BottomBarItem(
  screen: BottomBarScreen,
  currentDestination: NavDestination?,
  navController: NavHostController
) {
  val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
  val iconTintColor: Color =
    if (selected) colorResource(id = R.color.primary) else colorResource(id = R.color.secondary)
  val textColor: Color =
    if (selected) colorResource(id = R.color.text_default) else colorResource(id = R.color.secondary)
  val background: Color =
    if (selected) colorResource(id = R.color.secondary).copy(alpha = 0.5f) else Transparent

  Box(
    modifier = Modifier
      .clickable(onClick = {
        navController.navigate(screen.route) {
          popUpTo(navController.graph.findStartDestination().id)
          launchSingleTop = true
        }
      })
  ) {
    Row(
      modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      ConstraintLayout {
        val (icon, text, selectedBox) = createRefs()

        if (selected) {
          Box(modifier = Modifier
            .sizeIn(minHeight = 4.dp)
            .constrainAs(selectedBox) {
              start.linkTo(text.start, margin = (-4).dp)
              end.linkTo(text.end, margin = (-4).dp)
              top.linkTo(icon.top, margin = (-4).dp)
              bottom.linkTo(text.top, margin = 4.dp)
              width = Dimension.fillToConstraints
              height = Dimension.fillToConstraints
            }
            .background(background, shape = CircleShape)
          )
        }

        Icon(
          painter = painterResource(id = screen.icon),
          contentDescription = "icon",
          tint = iconTintColor,
          modifier = Modifier
            .constrainAs(icon) {
              start.linkTo(text.start)
              end.linkTo(text.end)
              bottom.linkTo(text.top, margin = 8.dp)
              width = Dimension.fillToConstraints
            }
        )

        Text(
          modifier = Modifier.constrainAs(text) {
            bottom.linkTo(parent.bottom)
          },
          text = stringResource(id = screen.title),
          color = textColor
        )
      }
    }
  }
}
package com.exchange.rate.tracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exchange.rate.tracker.CurrencyEntity
import com.exchange.rate.tracker.R

@Composable
fun CurrenciesContent(modifier: Modifier = Modifier) {
  val currencyList = listOf(
    CurrencyEntity(1, "EUR", 2.34256, false),
    CurrencyEntity(2, "USD", 1.325552, true),
    CurrencyEntity(3, "YSN", 33.42, false),
    CurrencyEntity(4, "YSN", 33.42, false),
  )

  val currencies: List<CurrencyEntity> = remember { currencyList }

  LazyColumn(modifier = modifier) {
    items(
      count = currencies.size,
      key = {
        currencies[it].id
      },
      itemContent = { index ->
        CurrencyItem(currencyEntity = currencies[index])

        Spacer(modifier = Modifier.padding(top = 8.dp))
      }
    )
  }
}

@Composable
private fun CurrencyItem(currencyEntity: CurrencyEntity) {
  val isSelected by remember { mutableStateOf(currencyEntity.isFavorite) }

  val textColor = colorResource(id = R.color.text_default)
  val favoriteIcon = if (isSelected) {
    painterResource(id = R.drawable.ic_favorites_on)
  } else {
    painterResource(id = R.drawable.ic_favorites_off)
  }
  val favoriteIconTint = if (isSelected) {
    colorResource(id = R.color.yellow)
  } else {
    LocalContentColor.current
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        color = colorResource(id = R.color.bg_card),
        shape = RoundedCornerShape(12.dp)
      )
      .padding(vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .weight(0.3f),
      text = currencyEntity.currencyName,
      color = textColor,
      fontSize = 14.sp,
      fontStyle = FontStyle(500)
    )

    Box(
      modifier = Modifier
        .weight(0.5f)
        .padding(end = 16.dp), contentAlignment = Alignment.CenterEnd
    ) {
      Text(
        text = "${currencyEntity.currencyValue}",
        color = textColor,
        fontSize = 16.sp,
        fontStyle = FontStyle(600),
      )
    }

    Icon(
      modifier = Modifier
        .padding(end = 16.dp)
        .clickable {

        },
      painter = favoriteIcon, contentDescription = "Favorite",
      tint = favoriteIconTint
    )

  }
}
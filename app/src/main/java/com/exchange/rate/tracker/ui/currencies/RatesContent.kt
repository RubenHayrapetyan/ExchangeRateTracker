package com.exchange.rate.tracker.ui.currencies

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
import com.exchange.rate.entity.local.RateEntity
import com.exchange.rate.tracker.R

@Composable
fun RatesContent(modifier: Modifier = Modifier, viewModel: CurrenciesViewModel) {

  val ratesEntity by viewModel.rates
  if (ratesEntity.isEmpty()) return

  val rates: List<RateEntity> = remember { ratesEntity }

  LazyColumn(modifier = modifier) {
    items(
      count = rates.size,
      key = {
        rates[it].rateName
      },
      itemContent = { index ->
        RateItem(rateEntity = rates[index])

        Spacer(modifier = Modifier.padding(top = 8.dp))
      }
    )
  }
}

@Composable
private fun RateItem(rateEntity: RateEntity) {
  val isSelected by remember { mutableStateOf(rateEntity.isFavorite) }

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
      text = rateEntity.rateName,
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
        text = "${rateEntity.rateValue}",
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
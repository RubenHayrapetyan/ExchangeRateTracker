package com.exchange.rate.tracker.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.exchange.rate.constants.Constants
import com.exchange.rate.entity.local.RateEntity
import com.exchange.rate.tracker.R
import com.exchange.rate.tracker.components.HorizontalLine
import com.exchange.rate.tracker.components.TopBar
import com.exchange.rate.tracker.ui.currencies.CurrenciesViewModel

@Composable
fun FavoritesScreen(viewModel: CurrenciesViewModel = hiltViewModel()) {

  LaunchedEffect(key1 = Constants.FAVORITE_RATE_KEY) {
    viewModel.getAllFavoriteRates()
  }

  val favoriteRates by viewModel.favoriteRates

  Column(modifier = Modifier.fillMaxSize()) {

    TopBar(
      title = stringResource(id = R.string.title_favorites),
      isBackEnabled = false
    )

    HorizontalLine()

    if (favoriteRates.isNotEmpty()) {
      FavoriteRatesContent(
        viewModel = viewModel,
        rates = favoriteRates
      )
    }
  }
}

@Composable
private fun FavoriteRatesContent(
  modifier: Modifier = Modifier,
  viewModel: CurrenciesViewModel,
  rates: List<RateEntity>
) {

  LazyColumn(
    modifier = modifier
      .fillMaxWidth()
      .padding(start = 16.dp, end = 16.dp, top = 16.dp)
  ) {
    items(
      count = rates.size,
      key = {
        rates[it].rateName
      },
      itemContent = { index ->
        RateItem(
          rateEntity = rates[index],
          viewModel = viewModel
        )

        Spacer(modifier = Modifier.padding(top = 8.dp))
      }
    )
  }
}

@Composable
private fun RateItem(rateEntity: RateEntity, viewModel: CurrenciesViewModel) {
  val textColor = colorResource(id = R.color.text_default)
  val rateName = "${rateEntity.baseRateName}/${rateEntity.rateName}"

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
      text = rateName,
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
          viewModel.unFavoriteRatesAndGetFavoriteRates(rateName = rateEntity.rateName)
        },
      painter = painterResource(id = R.drawable.ic_favorites_on),
      contentDescription = "Favorite",
      tint = colorResource(id = R.color.yellow)
    )
  }
}
package com.exchange.rate.tracker.ui.currencies

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.exchange.rate.constants.Constants
import com.exchange.rate.tracker.R
import com.exchange.rate.tracker.util.noRippleClickable

@Composable
fun CurrenciesScreen(
  navHostController: NavHostController,
  viewModel: CurrenciesViewModel = hiltViewModel()
) {

  LaunchedEffect(key1 = Constants.BASE_CURRENCY_KEY) {
    viewModel.getBaseCurrencies()
  }

  val error by viewModel.error

  ConstraintLayout(
    modifier = Modifier
      .fillMaxSize()
      .background(color = colorResource(id = R.color.on_primary))
  ) {

    val (title, filter, dropDown, background, currencies, errorMessage) = createRefs()

    // This is for grey background
    Box(modifier = Modifier
      .constrainAs(background) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(filter.bottom)
        width = Dimension.fillToConstraints
        height = Dimension.fillToConstraints
      }
      .background(color = colorResource(id = R.color.header))
    )

    Text(
      modifier = Modifier
        .padding(start = 16.dp, top = 10.dp)
        .constrainAs(title) {
          start.linkTo(parent.start)
          top.linkTo(parent.top)
        },
      text = stringResource(id = R.string.title_currencies),
      fontSize = 22.sp,
      fontWeight = FontWeight(700),
      color = colorResource(id = R.color.text_default)
    )

    if(error.isNotEmpty()) {
      ErrorText(
        modifier = Modifier.constrainAs(errorMessage) {
          top.linkTo(background.bottom, margin = 16.dp)
          start.linkTo(parent.start, margin = 16.dp)
          end.linkTo(parent.end, margin = 16.dp)
          width = Dimension.fillToConstraints
        },
        errorMessage = error
      )
    } else {
      RatesContent(
        modifier = Modifier.constrainAs(currencies) {
          top.linkTo(background.bottom, margin = 16.dp)
          start.linkTo(parent.start, margin = 16.dp)
          end.linkTo(parent.end, margin = 16.dp)
          bottom.linkTo(parent.bottom)
          width = Dimension.fillToConstraints
          height = Dimension.fillToConstraints
        },
        viewModel = viewModel
      )
    }

    Dropdown(
      modifier = Modifier
        .constrainAs(dropDown) {
          start.linkTo(parent.start, margin = 16.dp)
          end.linkTo(filter.start, margin = 8.dp)
          top.linkTo(title.bottom, margin = 12.dp)
          width = Dimension.fillToConstraints
        }
        .heightIn(min = 48.dp),
      viewModel = viewModel,
    )

    Filter(
      modifier = Modifier
        .constrainAs(filter) {
          end.linkTo(parent.end, margin = 16.dp)
          top.linkTo(title.bottom, margin = 12.dp)
        }
        .padding(bottom = 8.dp),
      navHostController = navHostController
    )
  }
}

@NonRestartableComposable
@Composable
private fun ErrorText(modifier: Modifier = Modifier, errorMessage: String) {
  Text(
    modifier = modifier,
    text = errorMessage
  )
}

@Composable
private fun Filter(modifier: Modifier = Modifier, navHostController: NavHostController) {

  IconButton(
    modifier = modifier
      .border(
        width = 1.dp,
        color = colorResource(id = R.color.secondary),
        shape = RoundedCornerShape(8.dp)
      )
      .background(
        color = colorResource(id = R.color.on_primary),
        shape = RoundedCornerShape(8.dp)
      ),
    onClick = {
      navHostController.navigate(Constants.FILTERS_ROUTE)
    }
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_filter), contentDescription = "Filter",
      tint = colorResource(id = R.color.primary)
    )
  }
}

@Composable
private fun Dropdown(
  viewModel: CurrenciesViewModel,
  modifier: Modifier = Modifier,
) {
  val baseCurrencies by viewModel.baseCurrencies
  if (baseCurrencies.isEmpty()) return

  var selectedItemId by rememberSaveable { mutableStateOf(0) }
  var expanded by remember { mutableStateOf(false) }

  LaunchedEffect(key1 = Constants.RATE_KEY) {
    viewModel.getRates(selectedCurrency = baseCurrencies[selectedItemId])
  }

  Column(
    modifier = modifier
      .noRippleClickable {
        expanded = !expanded
      }
      .border(
        width = 1.dp,
        color = colorResource(id = R.color.secondary),
        shape = RoundedCornerShape(8.dp)
      )
      .background(
        color = colorResource(id = R.color.on_primary),
        shape = RoundedCornerShape(8.dp)
      ),
    verticalArrangement = Arrangement.Center
  ) {
    val icArrow = if (expanded) {
      painterResource(id = R.drawable.ic_arrow_up)
    } else {
      painterResource(id = R.drawable.ic_arrow_down)
    }

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 48.dp)
        .padding(horizontal = 16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        modifier = Modifier.weight(1f),
        text = baseCurrencies[selectedItemId],
        color = colorResource(id = R.color.text_default),
        fontSize = 14.sp,
        fontWeight = FontWeight(500)
      )

      Icon(
        painter = icArrow,
        contentDescription = "Drop down arrow icon",
        tint = colorResource(id = R.color.primary)
      )
    }

    if (expanded) {
      DropdownItem(
        items = baseCurrencies,
        selectedItemIndex = selectedItemId,
        viewModel = viewModel,
        onBaseCurrencySelected = { selectedBaseCurrency ->
          expanded = false
          selectedItemId = selectedBaseCurrency
        }
      )
    }
  }
}

@Composable
private fun DropdownItem(
  items: List<String>,
  selectedItemIndex: Int,
  viewModel: CurrenciesViewModel,
  onBaseCurrencySelected: (Int) -> Unit
) {
  items.forEachIndexed { index, item ->
    val selectedTextBackground = if (selectedItemIndex == index) {
      colorResource(id = R.color.light_primary)
    } else {
      Color.Transparent
    }

    Text(
      modifier = Modifier
        .fillMaxWidth()
        .background(color = selectedTextBackground)
        .clickable {
          onBaseCurrencySelected.invoke(index)
          viewModel.getRates(selectedCurrency = item)
        }
        .padding(16.dp),
      text = item,
      color = colorResource(id = R.color.text_default),
      fontSize = 14.sp,
      fontWeight = FontWeight(500)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun CurrenciesScreenPreview() {
  // CurrenciesScreen()
}
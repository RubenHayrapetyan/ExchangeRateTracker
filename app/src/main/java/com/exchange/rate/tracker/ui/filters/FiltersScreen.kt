package com.exchange.rate.tracker.ui.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.exchange.rate.constants.Constants
import com.exchange.rate.entity.FilterType
import com.exchange.rate.entity.local.FilterModel
import com.exchange.rate.tracker.R
import com.exchange.rate.tracker.components.HorizontalLine
import com.exchange.rate.tracker.components.TopBar

@Composable
fun FiltersScreen(
  navHostController: NavHostController,
  selectedFilterTypeOrdinal: Int = FilterType.FROM_A_TO_Z.ordinal
) {

  var selectedFilterType by rememberSaveable { mutableStateOf(selectedFilterTypeOrdinal) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = colorResource(id = R.color.on_primary))
  ) {
    TopBar(
      navHostController = navHostController,
      title = stringResource(id = R.string.title_filters),
      isBackEnabled = true
    )

    HorizontalLine()

    Text(
      modifier = Modifier.padding(top = 16.dp, start = 16.dp),
      text = stringResource(id = R.string.sort_by).uppercase(),
      color = colorResource(id = R.color.text_secondary),
      fontWeight = FontWeight(700),
      fontSize = 12.sp
    )

    Spacer(modifier = Modifier.height(12.dp))

    FilterTypeContent(
      selectedFilterTypeOrdinal = selectedFilterTypeOrdinal,
      onFilterTypeSelected = {
        selectedFilterType = it.filterType.ordinal
      }
    )

    Spacer(modifier = Modifier.weight(1f))

    ApplyButton(
      navHostController = navHostController,
      selectedFilterTypeOrdinal = selectedFilterType
    )
  }
}

@Composable
private fun FilterTypeContent(
  selectedFilterTypeOrdinal: Int,
  onFilterTypeSelected: (FilterModel) -> Unit
) {
  val filterModelTypes = listOf(
    FilterModel(
      filterName = stringResource(id = R.string.filter_a_to_z),
      filterType = FilterType.FROM_A_TO_Z
    ),
    FilterModel(
      filterName = stringResource(id = R.string.filter_z_to_a),
      filterType = FilterType.FROM_Z_TO_A
    ),
    FilterModel(
      filterName = stringResource(id = R.string.filter_asc),
      filterType = FilterType.INCREASING_VALUE
    ),
    FilterModel(
      filterName = stringResource(id = R.string.filter_desc),
      filterType = FilterType.DECREASING_VALUE
    )
  )

  var selectedItem by rememberSaveable { mutableStateOf(filterModelTypes[selectedFilterTypeOrdinal].filterName) }

  filterModelTypes.forEach { filter ->
    FilterTypeItem(
      filterModel = filter,
      isSelected = filter.filterName == selectedItem,
      onFilterTypeSelected = {
        onFilterTypeSelected.invoke(it)
        selectedItem = it.filterName
      }
    )
  }
}

@Composable
private fun FilterTypeItem(
  isSelected: Boolean,
  filterModel: FilterModel,
  onFilterTypeSelected: (FilterModel) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Text(
      modifier = Modifier.weight(1f),
      text = filterModel.filterName,
      fontSize = 16.sp,
      fontWeight = FontWeight(500),
      color = colorResource(id = R.color.text_default)
    )

    RadioButton(
      selected = isSelected,
      onClick = {
        onFilterTypeSelected(filterModel)
      },
      colors = RadioButtonDefaults.colors(
        selectedColor = colorResource(id = R.color.primary),
        unselectedColor = colorResource(id = R.color.secondary)
      )
    )
  }
}

@Composable
private fun ApplyButton(
  navHostController: NavHostController,
  selectedFilterTypeOrdinal: Int
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
    contentAlignment = Alignment.BottomCenter
  ) {
    Button(
      onClick = {
        navHostController.previousBackStackEntry
          ?.savedStateHandle
          ?.set(
            key = Constants.ARG_FILTER,
            value = selectedFilterTypeOrdinal
          )
        navHostController.popBackStack()
      },
      modifier = Modifier
        .fillMaxWidth()
        .background(
          color = colorResource(id = R.color.primary),
          shape = RoundedCornerShape(100.dp)
        ),
      colors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.primary)
      )
    ) {
      Text(text = stringResource(id = R.string.button_apply))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun FiltersScreenPreview() {
  FiltersScreen(rememberNavController())
}
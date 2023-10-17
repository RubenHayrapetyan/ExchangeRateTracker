package com.exchange.rate.tracker.ui.currencies

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchange.rate.domain.usecase.FavoriteRateUseCase
import com.exchange.rate.domain.usecase.GetAllFavoriteRatesUseCase
import com.exchange.rate.domain.usecase.GetBaseCurrenciesUseCase
import com.exchange.rate.domain.usecase.GetCurrenciesUseCase
import com.exchange.rate.domain.usecase.InsertRatesUseCase
import com.exchange.rate.domain.usecase.UnFavoriteRateUseCase
import com.exchange.rate.entity.ActionResult
import com.exchange.rate.entity.FilterType
import com.exchange.rate.entity.local.RateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
  private val getBaseCurrenciesUseCase: GetBaseCurrenciesUseCase,
  private val getCurrenciesUseCase: GetCurrenciesUseCase,
  private val unFavoriteRateUseCase: UnFavoriteRateUseCase,
  private val favoriteRateUseCase: FavoriteRateUseCase,
  private val insertRatesUseCase: InsertRatesUseCase,
  private val getAllFavoriteRatesUseCase: GetAllFavoriteRatesUseCase
) : ViewModel() {

  val favoriteRates2 = mutableStateOf<Set<String>>(emptySet())

  private val _baseCurrencies by lazy { mutableStateOf<List<String>>(emptyList()) }
  val baseCurrencies: State<List<String>> = _baseCurrencies

  private val _rates by lazy { mutableStateOf<List<RateEntity>>(emptyList()) }
  val rates: State<List<RateEntity>> = _rates

  private val _favoriteRates by lazy { mutableStateOf<List<RateEntity>>(emptyList()) }
  val favoriteRates: State<List<RateEntity>> = _favoriteRates

  private val _error by lazy { mutableStateOf("") }
  val error: State<String> = _error

  fun getBaseCurrencies() {
    viewModelScope.launch {
      getBaseCurrenciesUseCase().collectLatest { currencies ->
        _baseCurrencies.value = currencies
      }
    }
  }

//  private fun getNewRatesAndMakeFavorite() : List<RateEntity> {
//      viewModelScope.launch {
//        getAllFavoriteRatesUseCase().collectLatest { favoriteRates ->
//           val oldFavorite = favoriteRates.filter {
//              it.isFavorite
//            }
//
//          //addAll(oldFavorite)
//        }
//      }
//  }

  private fun sortDataBySelectedFilter(
    rates: List<RateEntity>,
    filterTypeOrdinal: Int
  ): List<RateEntity> {
    return when (filterTypeOrdinal) {
      FilterType.FROM_A_TO_Z.ordinal -> rates.sortedBy { it.rateName }
      FilterType.FROM_Z_TO_A.ordinal -> rates.sortedByDescending { it.rateName }
      FilterType.INCREASING_VALUE.ordinal -> rates.sortedBy { it.rateValue }
      FilterType.DECREASING_VALUE.ordinal -> rates.sortedByDescending { it.rateValue }
      else -> rates
    }
  }

  fun getRates(selectedCurrency: String, filterTypeOrdinal: Int) {
    viewModelScope.launch {
      _error.value = ""
      getCurrenciesUseCase(selectedCurrency = selectedCurrency).let { result ->
        when (result) {
          is ActionResult.Success -> {
            val data = result.data
            data.forEach { // TODO remove
              if (it.isFavorite) {
                Log.v("updatedRates", "viewModel favorite = ${it.rateName}")
              }
            }
            val sortedData =
              sortDataBySelectedFilter(rates = data, filterTypeOrdinal = filterTypeOrdinal)

            _rates.value = sortedData
          }

          is ActionResult.Error -> {
            result.e.message?.let {
              _error.value = it
            } ?: run {
              _error.value = "Something went wrong"
            }
          }
        }
      }
    }
  }

  fun favoriteRate(rateName: String, baseRateName: String) {
    viewModelScope.launch {
      favoriteRateUseCase(rateName = rateName, baseRateName = baseRateName)
      favoriteRates2.value = favoriteRates2.value + rateName
    }
  }

  fun unFavoriteRatesAndGetFavoriteRates(rateName: String) {
    viewModelScope.launch {
      unFavoriteRateUseCase(rateName = rateName)
      favoriteRates2.value = favoriteRates2.value - rateName
      getAllFavoriteRates()
    }
  }

  fun getAllFavoriteRates() {
    viewModelScope.launch {
      getAllFavoriteRatesUseCase().collectLatest {
        _favoriteRates.value = it
      }
    }
  }
}
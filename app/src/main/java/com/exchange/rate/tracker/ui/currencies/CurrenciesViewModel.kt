package com.exchange.rate.tracker.ui.currencies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchange.rate.domain.usecase.FavoriteRateUseCase
import com.exchange.rate.domain.usecase.GetAllFavoriteRatesUseCase
import com.exchange.rate.domain.usecase.GetBaseCurrenciesUseCase
import com.exchange.rate.domain.usecase.GetCurrenciesUseCase
import com.exchange.rate.domain.usecase.UnFavoriteRateUseCase
import com.exchange.rate.entity.ActionResult
import com.exchange.rate.entity.FilterType
import com.exchange.rate.entity.local.RateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
  private val getAllFavoriteRatesUseCase: GetAllFavoriteRatesUseCase
) : ViewModel() {

  private val _baseCurrencies by lazy { mutableStateOf<List<String>>(emptyList()) }
  val baseCurrencies: State<List<String>> = _baseCurrencies

  private val _selectedBaseCurrency by lazy { mutableStateOf("") }
  val selectedBaseCurrency: State<String> = _selectedBaseCurrency

  private val _rates by lazy { mutableStateOf<List<RateEntity>>(emptyList()) }
  val rates: State<List<RateEntity>> = _rates

  private val _favoriteRates by lazy { mutableStateOf<List<RateEntity>>(emptyList()) }
  val favoriteRates: State<List<RateEntity>> = _favoriteRates

  private val _error by lazy { mutableStateOf("") }
  val error: State<String> = _error

  val favoriteList = mutableListOf<String>()

  fun selectBaseCurrency(baseCurrency: String) {
    _selectedBaseCurrency.value = baseCurrency
  }

  fun getBaseCurrencies() {
    viewModelScope.launch {
      getBaseCurrenciesUseCase().collectLatest { currencies ->
        _baseCurrencies.value = currencies
      }
      _selectedBaseCurrency.value = _baseCurrencies.value.first()
    }
  }

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
      getCurrenciesUseCase(selectedCurrency = selectedCurrency).let { result ->
        when (result) {
          is ActionResult.Success -> {
            _error.value = ""
            val data = result.data
            val updatedNewRates = updateNewRatesWithOldRates(
              newRates = data,
              oldRatesFlow = getAllFavoriteRatesUseCase()
            )
            val sortedData =
              sortDataBySelectedFilter(
                rates = updatedNewRates,
                filterTypeOrdinal = filterTypeOrdinal
              )
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
      favoriteList.add(rateName)
    }
  }

  fun unFavoriteRatesAndGetFavoriteRates(rateName: String) {
    viewModelScope.launch {
      unFavoriteRateUseCase(rateName = rateName)
      favoriteList.removeIf { it == rateName }
      getAllFavoriteRates()
    }
  }

  fun getAllFavoriteRates() {
    viewModelScope.launch {
      getAllFavoriteRatesUseCase().collectLatest {
        it.forEach {
          favoriteList.add(it.rateName)
        }
        _favoriteRates.value = it
      }
    }
  }

  private suspend fun updateNewRatesWithOldRates(
    newRates: List<RateEntity>,
    oldRatesFlow: Flow<List<RateEntity>>
  ): List<RateEntity> {

    favoriteList.clear()

    val oldRatesList = oldRatesFlow.first()
    // Create a set of rate names from the oldRatesList
    val oldRateNames = oldRatesList.map { it.rateName }

    // Update the newRates by setting isFavorite to true for items in oldRatesList
    return newRates.map { rate ->
      val isOldRateFavorite = oldRateNames.contains(rate.rateName)
      if (isOldRateFavorite) {
        favoriteList.add(rate.rateName)
      }
      rate.copy(isFavorite = isOldRateFavorite)
    }
  }
}
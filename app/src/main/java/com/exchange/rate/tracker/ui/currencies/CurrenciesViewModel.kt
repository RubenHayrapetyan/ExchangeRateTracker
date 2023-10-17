package com.exchange.rate.tracker.ui.currencies

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
import com.exchange.rate.entity.local.RateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
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

  fun getRates(selectedCurrency: String) {
    viewModelScope.launch {
      _error.value = ""
      getCurrenciesUseCase(selectedCurrency = selectedCurrency).let { result ->
        when (result) {
          is ActionResult.Success -> {
            insertRatesUseCase(result.data)
            _rates.value = result.data
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
    }
  }

  fun unFavoriteRatesAndGetFavoriteRates(rateName: String) {
    viewModelScope.launch {
      unFavoriteRateUseCase(rateName = rateName)
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
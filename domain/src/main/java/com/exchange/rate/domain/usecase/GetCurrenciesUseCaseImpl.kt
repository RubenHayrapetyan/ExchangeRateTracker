package com.exchange.rate.domain.usecase

import android.util.Log
import com.exchange.rate.data.repository.CurrencyRepo
import com.exchange.rate.data.repository.RateRepo
import com.exchange.rate.entity.ActionResult
import com.exchange.rate.entity.local.RateEntity
import com.exchange.rate.entity.local.RatesModel
import com.exchange.rate.entity.toCurrencyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrenciesUseCaseImpl @Inject constructor(
  private val currencyRepo: CurrencyRepo,
  private val rateRepo: RateRepo
) :
  GetCurrenciesUseCase {

  override suspend fun invoke(selectedCurrency: String): ActionResult<List<RateEntity>> =
    withContext(Dispatchers.IO) {
      currencyRepo.getCurrencies(selectedCurrency = selectedCurrency).let { result ->
        when (result) {
          is ActionResult.Success -> {
            val currencyEntity = result.data.toCurrencyEntity()

            if (currencyEntity.success) {
              val rateEntity = mutableListOf<RateEntity>()
              val fields = RatesModel::class.java.declaredFields
              fields.forEach { field ->
                field.isAccessible = true
                if (field.get(currencyEntity.ratesModel) is Double) {
                  val fieldValue = field.get(currencyEntity.ratesModel) as Double
                  rateEntity.add(
                    RateEntity(
                      rateName = field.name.uppercase(),
                      rateValue = fieldValue,
                      isFavorite = false
                    )
                  )
                }
              }

              val updatedNewRates = updateNewRatesWithOldRates(
                newRates = rateEntity,
                oldRatesFlow = rateRepo.getAllFavoriteRates()
              )
              updatedNewRates.forEach {
                if (it.isFavorite) {
                  Log.v("updatedRates", "useCase favorite = ${it.rateName}")
                }
              }

              ActionResult.Success(data = updatedNewRates)
            } else {
              ActionResult.Error(Throwable("Something went wrong"))
            }
          }

          is ActionResult.Error -> result
        }
      }
    }


  private suspend fun updateNewRatesWithOldRates(
    newRates: List<RateEntity>,
    oldRatesFlow: Flow<List<RateEntity>>
  ): List<RateEntity> {
    val oldRatesList = oldRatesFlow.first()
    // Create a set of rate names from the oldRatesList
    val oldRateNames = oldRatesList.map { it.rateName }.toSet()

    // Update the newRates by setting isFavorite to true for items in oldRatesList
    return newRates.map { rate ->
      rate.copy(isFavorite = oldRateNames.contains(rate.rateName))
    }
  }

}
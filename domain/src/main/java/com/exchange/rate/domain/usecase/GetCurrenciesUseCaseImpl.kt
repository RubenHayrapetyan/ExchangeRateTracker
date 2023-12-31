package com.exchange.rate.domain.usecase

import com.exchange.rate.data.repository.CurrencyRepo
import com.exchange.rate.data.util.IODispatcher
import com.exchange.rate.entity.ActionResult
import com.exchange.rate.entity.local.RateEntity
import com.exchange.rate.entity.local.RatesModel
import com.exchange.rate.entity.toCurrencyEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrenciesUseCaseImpl @Inject constructor(
  private val currencyRepo: CurrencyRepo,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher
  ) :
  GetCurrenciesUseCase {

  override suspend fun invoke(selectedCurrency: String): ActionResult<List<RateEntity>> =
    withContext(ioDispatcher) {
      currencyRepo.getCurrencies(selectedCurrency = selectedCurrency).let { result ->
        when (result) {
          is ActionResult.Success -> {
            val currencyEntity = result.data.toCurrencyEntity()

            if (currencyEntity.success) {
              val rates = mutableListOf<RateEntity>()
              val fields = RatesModel::class.java.declaredFields
              fields.forEach { field ->
                field.isAccessible = true
                if (field.get(currencyEntity.ratesModel) is Double) {
                  val fieldValue = field.get(currencyEntity.ratesModel) as Double
                  rates.add(
                    RateEntity(
                      rateName = field.name.uppercase(),
                      rateValue = fieldValue,
                      isFavorite = false
                    )
                  )
                }
              }

              ActionResult.Success(data = rates)
            } else {
              ActionResult.Error(errorMessage = "Something went wrong", errorCode = "")
            }
          }

          is ActionResult.Error -> result
        }
      }
    }
}
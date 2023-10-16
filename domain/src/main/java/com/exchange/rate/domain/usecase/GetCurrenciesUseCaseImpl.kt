package com.exchange.rate.domain.usecase

import com.exchange.rate.data.repository.CurrencyRepo
import com.exchange.rate.entity.ActionResult
import com.exchange.rate.entity.local.RateEntity
import com.exchange.rate.entity.local.RatesModel
import com.exchange.rate.entity.toCurrencyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrenciesUseCaseImpl @Inject constructor(private val currencyRepo: CurrencyRepo) :
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
              ActionResult.Success(data = rateEntity)
            } else {
              ActionResult.Error(Throwable("Something went wrong"))
            }
          }

          is ActionResult.Error -> result
        }
      }
    }
}
package com.exchange.rate.data.di

import com.exchange.rate.constants.Request
import com.exchange.rate.data.remote.CurrencyService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
object RemoteModule {
  @Provides
  fun provideGson(): Gson = GsonBuilder().create()

  @Provides
  fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

  @Provides
  fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
    readTimeout(1, TimeUnit.MINUTES)
    connectTimeout(1, TimeUnit.MINUTES)
    callTimeout(1, TimeUnit.MINUTES)
    writeTimeout(1, TimeUnit.MINUTES)
  }.build()

  @Provides
  fun provideRetrofit(
    converterFactory: GsonConverterFactory,
    okHttpClient: OkHttpClient,
  ): Retrofit = Retrofit.Builder().apply {
    baseUrl(Request.BASE_URL)
    addConverterFactory(converterFactory)
    client(okHttpClient)
  }.build()

  @Provides
  fun provideCurrencyService(retrofit: Retrofit): CurrencyService =
    retrofit.create(CurrencyService::class.java)
}

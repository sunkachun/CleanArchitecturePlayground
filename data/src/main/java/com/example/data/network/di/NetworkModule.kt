package com.example.data.network.di

import com.example.data.network.GovServiceGenerator
import com.example.data.network.client.AppOkHttpClientProvider
import com.example.data.network.client.OkHttpClientProvider
import com.example.data.weather.weatherforecast.WeatherForecastService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideWeatherForecastServices(serviceGenerator: GovServiceGenerator): WeatherForecastService {
        return serviceGenerator.retrofit()
            .create(WeatherForecastService::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {

        @Binds
        fun bindOkHttpGenerator(generator: AppOkHttpClientProvider): OkHttpClientProvider
    }
}
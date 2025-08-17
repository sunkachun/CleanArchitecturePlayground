package com.example.presentation.weatherforecast

import androidx.lifecycle.ViewModel
import com.example.domain.weatherforecast.GetNineDayWeatherForecast
import com.example.presentation.extension.observeOnMain
import com.example.presentation.extension.subscribeOnIO
import com.example.presentation.weather.weatherforecast.WeatherForecastSectionMapper
import com.example.presentation.weather.weatherforecast.model.NineDayWeatherForecastSection
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    private val getNineDayWeatherForecast: GetNineDayWeatherForecast,
    private val mapper: WeatherForecastSectionMapper,
) : ViewModel() {

    // Jetpack Compose mostly use state
    private val _nineDayWeatherForecastFlow = MutableStateFlow<NineDayWeatherForecastSection?>(null)
    val nineDayWeatherForecastFlow: StateFlow<NineDayWeatherForecastSection?> get() = _nineDayWeatherForecastFlow

    private var disposable: Disposable? = null

    init {
        fetchNineDayWeatherForecast()
    }

    private fun fetchNineDayWeatherForecast() {
        disposable = getNineDayWeatherForecast()
            .map { mapper.toSection(it) }
            .subscribeOnIO()
            .observeOnMain()
            .subscribe(
                { _nineDayWeatherForecastFlow.value = it },
                Timber::e
            )
    }
}
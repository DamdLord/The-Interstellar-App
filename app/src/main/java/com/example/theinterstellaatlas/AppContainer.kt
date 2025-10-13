package com.example.theinterstellaatlas

import com.example.theinterstellaatlas.retrofitAndApi.APIs
import com.example.theinterstellaatlas.retrofitAndApi.NetworkRepository
import com.example.theinterstellaatlas.retrofitAndApi.RetrofitInstance.contentType
import com.example.theinterstellaatlas.retrofitAndApi.RetrofitInstance.json
import com.example.theinterstellaatlas.retrofitAndApi.WeatherApi
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

interface AppContainer{
    val repo: NetworkRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "https://restcountries.com/v3.1/"

    private  val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/"

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS) // default 10s
        .readTimeout(60, TimeUnit.SECONDS)    // allow longer server response time
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @OptIn(ExperimentalSerializationApi::class)
    private val restCountriesRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(client)
        .build()

    private val restCountriesRetrofitService: APIs by lazy {
        restCountriesRetrofit.create(APIs::class.java)
    }


    @OptIn(ExperimentalSerializationApi::class)
    private val openWeatherRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(OPEN_WEATHER_BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(client)
        .build()

    private val openWeatherRetrofitService: WeatherApi by lazy {
        openWeatherRetrofit.create(WeatherApi::class.java)
    }

    override val repo by lazy {
        NetworkRepository(api = restCountriesRetrofitService, weatherApi = openWeatherRetrofitService)

    }
}
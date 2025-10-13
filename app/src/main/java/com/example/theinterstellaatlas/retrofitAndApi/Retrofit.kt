package com.example.theinterstellaatlas.retrofitAndApi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit

interface AppRepository{
    suspend fun getAllCountries(): Response<List<Country>>
    suspend fun getCountriesByRegion(region: String): Response<List<Country>>
    suspend  fun getCountryByName(name: String): Response<List<Country>>
    suspend fun getCountriesByCodes(codes: String): Response<List<Country>>
    suspend fun fetchData(lat: Double, lon: Double, apiid: String):Response<WeatherResponse>
    suspend fun getGeoCoding(cityName: String, apiid: String):List<GeocodingResponse>
}

class NetworkRepository(
    private val api: APIs,
    private val weatherApi: WeatherApi
) : AppRepository{
    override suspend fun getAllCountries(): Response<List<Country>> = api.getAllCountries()
    override suspend fun getCountriesByRegion(region: String): Response<List<Country>> = api.getCountriesByRegion(region)
    override suspend fun getCountryByName(name: String): Response<List<Country>> = api.getCountryByName(name)
    override suspend fun getCountriesByCodes(codes: String): Response<List<Country>> = api.getCountriesByCodes(codes)
    override suspend fun fetchData(lat: Double, lon: Double, apiid: String) = weatherApi.getCurrentWeather(lat,lon,apiid)
    override suspend fun getGeoCoding(cityName: String, apiid: String): List<GeocodingResponse> = weatherApi.getCoordinates(cityName, apiid,)
}

object RetrofitInstance{
    val json = Json { ignoreUnknownKeys = true }
    val contentType = "application/json".toMediaType()

    @OptIn(ExperimentalSerializationApi::class)
    val api: APIs by lazy {
        Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(APIs::class.java)
    }
}

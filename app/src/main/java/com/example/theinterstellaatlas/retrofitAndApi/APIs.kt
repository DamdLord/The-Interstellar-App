package com.example.theinterstellaatlas.retrofitAndApi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
@Serializable
data class Country(
    val flags: Flags,
    val name: Name,
    val cca2: String? = null,
    val cca3: String? = null,
    val capital: List<String>?,
    val region: String?,
    val subregion: String?,
    val borders: List<String>?,
    val population: Long?
)


@Serializable
data class Flags(
    val png: String?,
    val svg: String?,
    val alt: String?
)

@Serializable
data class Name(
    val common: String,
    val official: String,
    val nativeName: Map<String, NativeName>?
)

@Serializable
data class NativeName(
    val official: String?,
    val common: String?
)


@Serializable
data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val rain: Rain? = null,
    val clouds: Clouds,
    val sys: Sys,
    val name: String
)

@Serializable
data class Coord(val lon: Double, val lat: Double)

@Serializable
data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class Wind(val speed: Double, val deg: Int)

@Serializable
data class Rain(@SerialName("1h") val oneHour: Double? = null)

@Serializable
data class Clouds(val all: Int)

@Serializable
data class Sys(val country: String, val sunrise: Long, val sunset: Long)

@Serializable
data class GeocodingResponse(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double
)



interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // or "imperial"
    ):Response<WeatherResponse>

    @GET("geo/1.0/direct")
    suspend fun getCoordinates(
        @Query("q") location: String,
        @Query("appid") apiKey: String,
        @Query("limit") limit: Int = 1
    ): List<GeocodingResponse>
}



    interface APIs{
    @GET("all")
    suspend fun getAllCountries(
        @Query("fields") fields: String =
            "name,capital,region,subregion,population,flags,cca2,cca3,borders"
    ): Response<List<Country>>


    @GET("region/{region}")
    suspend fun getCountriesByRegion(
        @Path("region") region: String,
        @Query("fields") fields: String =
            "name,capital,region,subregion,population,flags,borders"
    ): Response<List<Country>>

    @GET("name/{name}")
    suspend fun getCountryByName(
        @Path("name") name: String,
        @Query("fields") fields: String =
            "name,capital,region,subregion,population,flags,borders"
    ): Response<List<Country>>

    @GET("alpha")
    suspend fun getCountriesByCodes(
        @Query("codes") codes: String
    ): Response<List<Country>>

}
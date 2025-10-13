package com.example.theinterstellaatlas

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.theinterstellaatlas.retrofitAndApi.AppRepository
import com.example.theinterstellaatlas.retrofitAndApi.Country
import com.example.theinterstellaatlas.retrofitAndApi.Flags
import com.example.theinterstellaatlas.retrofitAndApi.Name
import com.example.theinterstellaatlas.retrofitAndApi.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val flag:Flags = Flags(
        png = "",
        svg = "",
        alt = ""
    ),
    val name:Name = Name(
        common = "Nigeria",
        official = "",
        nativeName = mapOf()
    ),
    val capital:List<String> = emptyList(),
    val countrySearched: String = "",
    val regionSelected: String = "",
    val countries: List<Country> = emptyList(),
    val countryToShowDetails: Country = Country(
        flags = flag,
        name = name,
        population = 7484904,
        region = "Africa",
        subregion = "Eastern Africa",
        borders = listOf("",""),
        cca2 = "",
        cca3 = "",
        capital = capital
    ),
    val isLoading: Boolean = true,
    val hasWorked: Boolean = false,
    val errorMessage: String? = null
)
class AppViewModel(
    private val appRepository: AppRepository
): ViewModel(){
    private var cityToLookFor by mutableStateOf("")
    var countryToShowDetails by mutableStateOf<Country?>(null)
    var countryToShowDetailsBorder by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var hasWorked by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    private var currentLocationLatitude by mutableDoubleStateOf(0.00)
    private var currentLocationLongitude by mutableDoubleStateOf(0.00)

    private var countrySearched by mutableStateOf("")
    private var regionSelected by mutableStateOf("")
    private val _uiState = MutableStateFlow(UiState())
    val uiState  : StateFlow<UiState> = _uiState
    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    var countries: StateFlow<List<Country>> = _countries
    private val _currentPage = MutableStateFlow(0)


    fun updateCountryToShowDetails(country: Country){
        countryToShowDetails = country
    }

    fun updateCityToLookFor(city:String){
        cityToLookFor = city
    }

    fun updateCurrentLocationLatitude(lat:Double){
        currentLocationLatitude = lat
    }
    fun updateCurrentLocationLongitude(long:Double){
        currentLocationLongitude = long
    }

    fun updateCountrySearched(input: String){
        countrySearched = input
    }
    fun updateRegionSelected(input: String){
        regionSelected = input
    }

    fun updateUiStateCurrentCountry(input: String){
        _uiState.update {
            it.copy(
                countrySearched = input
            )
        }
    }

    fun updateUiStateRegion(input: String){
        _uiState.update {
            it.copy(
                regionSelected = input
            )
        }
    }

    fun updateUiStateCountryToShowDetails(country: Country){
        _uiState.update {
            it.copy(
                countryToShowDetails = country
            )
        }
    }

    private fun updateUiStateCountries(input: List<Country>){
        _uiState.update {
            it.copy(
                countries = input
            )
        }
    }

    fun fetchCoordinatesFromCity() {
        viewModelScope.launch {
            try {
                val city = cityToLookFor
                val apiKey = "a8a3c41606d44b719c209cbbd9eb9115"
                val result = appRepository.getGeoCoding(city, apiKey)
                if (result.isNotEmpty()) {
                    val location = result[0]
                    updateCurrentLocationLatitude(location.lat)
                    updateCurrentLocationLongitude(location.lon)
                    fetchWeather()
                } else {
                    errorMessage = "City not found"
                }
            } catch (e: Exception) {
                errorMessage = "Error fetching location: ${e.message}"
            }
        }
    }

    fun fetchWeather() {
        viewModelScope.launch {
            try {
                val lat = currentLocationLatitude
                val lon = currentLocationLongitude
                val apiKey = "a8a3c41606d44b719c209cbbd9eb9115"
                val response =appRepository.fetchData(lat,lon,apiKey)
                _weatherData.value = response.body()
            }catch(e:Exception){
                errorMessage = e.message.toString()
            }
        }
    }
    fun getBorderByCodes(codes:List<String>){
        viewModelScope.launch {
            try {
                val borderCodes = codes.joinToString(",")
                val response = appRepository.getCountriesByCodes(borderCodes!!)
                if (response.isSuccessful) {
                    val borderCountries = response.body()
                    val borderNames = borderCountries?.map { it.name.common }
                    countryToShowDetailsBorder = borderNames?.joinToString(", ") ?: ""
                }
            }catch (e: Exception){
                Log.e("borders", "Error fetching borders: ${e.message}")
            }
        }
    }

    fun getAllCountries(){
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, hasWorked = false) }
                isLoading = true
                hasWorked = false
                val response = appRepository.getAllCountries()
                if (response.isSuccessful){
                    isLoading = false
                    hasWorked = true
                    _countries.value = response.body()!!
                    _uiState.update { it.copy(isLoading = false, hasWorked = true) }
                    updateUiStateCountries(countries.value)
                }else{
                    isLoading = false
                    hasWorked = false
                    errorMessage = response.message()
                    _uiState.update { it.copy(isLoading = false, hasWorked = false) }
                }
            }catch (e: Exception){
                Log.e("fetchAllCountries", "Error fetching recommendations: ${e.message}")
                e.printStackTrace()
                isLoading = false
                hasWorked = false
                _uiState.update { it.copy(isLoading = false, hasWorked = false) }
            }
        }
    }

    fun getCountriesByRegion(){
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, hasWorked = false) }
                isLoading = true
                hasWorked = false
                val response = appRepository.getCountriesByRegion(regionSelected)
                if (response.isSuccessful){
                    isLoading = false
                    hasWorked = true
                    _countries.value = response.body()!!
                    _uiState.update { it.copy(isLoading = false, hasWorked = true, countries = response.body()!!) }
                    updateUiStateCountries(countries.value)
                }else{
                    isLoading = false
                    hasWorked = false
                    errorMessage = response.message()
                    _uiState.update { it.copy(isLoading = false, hasWorked = false) }
                }
            }catch (e: Exception){
                Log.e("fetchAllCountries", "Error fetching recommendations: ${e.message}")
                e.printStackTrace()
                isLoading = false
                hasWorked = false
                _uiState.update { it.copy(isLoading = false, hasWorked = false) }
            }
        }
    }

    fun getCountriesByName(){
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, hasWorked = false) }
                isLoading = true
                hasWorked = false
                val response = appRepository.getCountryByName(countrySearched)
                if (response.isSuccessful){
                    isLoading = false
                    hasWorked = true
                    _countries.value = response.body()!!
                    _uiState.update { it.copy(isLoading = false, hasWorked = true, countries = response.body()!!) }
                    updateUiStateCountries(countries.value)
                }else{
                    isLoading = false
                    hasWorked = false
                    errorMessage = response.message()
                    _uiState.update { it.copy(isLoading = false, hasWorked = false) }
                }
            }catch (e: Exception){
                Log.e("fetchAllCountries", "Error fetching recommendations: ${e.message}")
                e.printStackTrace()
                isLoading = false
                hasWorked = false
                _uiState.update { it.copy(isLoading = false, hasWorked = false) }
            }
        }
    }



    companion object{
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as InterStellaAtlasApplication)
                val  appRepository = application.container.repo
                AppViewModel(appRepository)
            }
        }
    }
}
package com.example.theinterstellaatlas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.theinterstellaatlas.AppViewModel
import com.example.theinterstellaatlas.Screens
import com.example.theinterstellaatlas.retrofitAndApi.Country
import com.example.theinterstellaatlas.retrofitAndApi.WeatherResponse
import kotlin.math.roundToInt

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    country: Country,
    appViewModel: AppViewModel,
    navController: NavController,
    weatherResponse: WeatherResponse?
){
    val iconCode = weatherResponse?.weather?.get(0)?.icon
    val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
    Scaffold {innerPadding ->
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                navController.navigate(Screens.HOME.name){
                    popUpTo(Screens.DETAILS.name){inclusive = true}
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Box (
            modifier = modifier.padding(innerPadding)
        ){
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Box {
                    Column {
                        AsyncImage(
                            model = country.flags.png,
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                            modifier = modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                        Spacer(modifier = modifier.height(10.dp))
                        country.flags.alt?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.height(20.dp))
                OutlinedCard(
                    elevation = CardDefaults.cardElevation(15.dp),
                    modifier = modifier.fillMaxWidth(),
                    //colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = modifier.padding(10.dp).fillMaxWidth()
                    ) {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = country.name.common,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 25.sp
                            )
                        }
                        Spacer(modifier = modifier.height(10.dp))
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Region:",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = modifier.width(10.dp))
                            country.region?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 20.sp
                                )
                            }
                        }
                        Spacer(modifier = modifier.height(10.dp))
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sub Region:",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = modifier.width(10.dp))
                            country.subregion?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 20.sp
                                )
                            }

                        }
                        Spacer(modifier = modifier.height(10.dp))
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Capital:",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = modifier.width(10.dp))
                            country.capital?.get(0)?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 20.sp
                                )
                            }
                        }
                        Spacer(modifier = modifier.height(10.dp))
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Border:",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = modifier.width(10.dp))
                            Text(
                                text = appViewModel.countryToShowDetailsBorder,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp,
                                modifier = modifier.padding(top=10.dp)
                            )
                        }
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Coordination:",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = modifier.width(10.dp))
                            if (weatherResponse != null) {
                                Text(
                                    text = "Longitude: ${weatherResponse.coord?.lon}  Latitude: ${weatherResponse.coord.lat}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp,
                                    modifier = modifier.padding(top=10.dp)
                                )
                            }

                        }
                    }
                }
                Spacer(modifier = modifier.height(20.dp))

                if (weatherResponse != null){
                    Card(modifier = modifier.fillMaxWidth()) {
                        Box(modifier = modifier.fillMaxWidth()) {
                            Row {
                                AsyncImage(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .data(iconUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                    modifier = modifier
                                        .width(150.dp)
                                        .height(200.dp)
                                )
                                Spacer(modifier = modifier.width(5.dp))
                                AsyncImage(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .data(iconUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                    modifier = modifier
                                        .width(150.dp)
                                        .height(200.dp)
                                )
                                Spacer(modifier = modifier.width(5.dp))
                                AsyncImage(
                                    model = ImageRequest.Builder(context = LocalContext.current)
                                        .data(iconUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                    modifier = modifier
                                        .width(150.dp)
                                        .height(200.dp)
                                )
                            }
                            Column(
                                modifier = modifier.fillMaxWidth().padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = "${weatherResponse.main.temp.roundToInt()}°",
                                    fontSize =  70.sp,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )

                                Text(
                                    text = weatherResponse.weather[0].main,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = weatherResponse.weather[0].description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    if (weatherResponse.rain != null) {
                                        OutlinedCard(
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = modifier.size(111.dp).padding(2.dp),
                                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                        ) {
                                            Column(
                                                modifier = modifier.padding(4.dp)
                                            ) {
                                                Text(
                                                    text = "Precipitation",
                                                    color = Color.LightGray,
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 12.sp
                                                )
                                                Text(
                                                    text = "${weatherResponse.rain.oneHour}mm/h",
                                                    fontWeight = FontWeight.SemiBold,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White,
                                                    fontSize = 25.sp
                                                )
                                            }
                                        }
                                    }
                                    OutlinedCard(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = modifier.size(111.dp).padding(2.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = modifier.padding(4.dp)
                                        ) {
                                            Text(
                                                text = "Feels like",
                                                color = Color.LightGray,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text = "${weatherResponse.main.feelsLike.roundToInt()}°",
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center,
                                                color = Color.White,
                                                fontSize = 20.sp
                                            )
                                            Text(
                                                text = if(weatherResponse.main.feelsLike > weatherResponse.main.temp){
                                                    "It feels warmer than the actual temperature"
                                                }else if (weatherResponse.main.feelsLike < weatherResponse.main.temp){
                                                    "It feels cooler than the actual temperature"
                                                }else{"It feels just right"},
                                                color = Color.White,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 11.sp,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = modifier.fillMaxWidth()
                                ) {
                                    OutlinedCard(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = modifier.size(111.dp).padding(2.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = modifier.padding(4.dp)
                                        ) {
                                            Text(
                                                text = "Minimum Temp.",
                                                color = Color.LightGray,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text = "${weatherResponse.main.tempMin.roundToInt()}°",
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center,
                                                color = Color.White,
                                                fontSize = 30.sp
                                            )
                                        }
                                    }
                                    OutlinedCard(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = modifier.size(111.dp).padding(2.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = modifier.padding(4.dp)
                                        ) {
                                            Text(
                                                text = "Maximum Temp.",
                                                color = Color.LightGray,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text = "${weatherResponse.main.tempMax.roundToInt()}°",
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center,
                                                color = Color.White,
                                                fontSize = 30.sp
                                            )
                                        }
                                    }
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = modifier.fillMaxWidth()
                                ) {
                                    OutlinedCard(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = modifier.size(111.dp).padding(2.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = modifier.padding(4.dp)
                                        ) {
                                            Text(
                                                text = "Humidity",
                                                color =  Color.LightGray,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text = "${weatherResponse.main.humidity}%",
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White,
                                                textAlign = TextAlign.Center,
                                                fontSize = 30.sp,
                                            )
                                            Text(
                                                text = if (weatherResponse.main.humidity > 70){"Humidity is relatively high"} else{"Humidity is relatively low"},
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                    OutlinedCard(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = modifier.size(111.dp).padding(2.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = modifier.padding(4.dp)
                                        ) {
                                            Text(
                                                text = "Pressure",
                                                color = Color.LightGray,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text = "${weatherResponse.main.pressure}hPa",
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center,
                                                color = Color.White,
                                                fontSize = 25.sp
                                            )
                                        }
                                    }

                                }
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    OutlinedCard(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = modifier.size(111.dp).padding(2.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = modifier.padding(4.dp)
                                        ) {
                                            Text(
                                                text = "Wind Speed",
                                                color = Color.LightGray,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text = "${weatherResponse.wind.speed}m/s",
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center,
                                                color = Color.White,
                                                fontSize = 25.sp
                                            )
                                        }
                                    }
                                    OutlinedCard(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = modifier.size(111.dp).padding(2.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = modifier.padding(4.dp)
                                        ){
                                            Text(
                                                text = "Wind direction",
                                                color = Color.LightGray,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 12.sp
                                            )
                                            Text(
                                                text ="${weatherResponse.wind.deg}°",
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center,
                                                color = Color.White,
                                                fontSize = 25.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
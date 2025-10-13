package com.example.theinterstellaatlas.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toString
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.theinterstellaatlas.AppViewModel
import com.example.theinterstellaatlas.R
import com.example.theinterstellaatlas.retrofitAndApi.Country
import com.example.theinterstellaatlas.retrofitAndApi.Flags
import com.example.theinterstellaatlas.retrofitAndApi.Name
import kotlinx.coroutines.launch

@Composable
fun CountryTile(
    country: Country,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    appViewModel: AppViewModel
){
    val coroutineScope = rememberCoroutineScope()
    OutlinedCard(
        onClick = {
            coroutineScope.launch {
                appViewModel.updateUiStateCountryToShowDetails(country)
                appViewModel.updateCountryToShowDetails(country)

                if (country.borders?.isNotEmpty() == true){
                    appViewModel.getBorderByCodes(country.borders)
                }
                appViewModel.updateCityToLookFor(country.name.common)
                appViewModel.fetchCoordinatesFromCity()
                onClick()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        //colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
        ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            //horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = country.flags.png,
                contentDescription = null,
                placeholder = painterResource(R.drawable.flag_placeholder),
                modifier = modifier.size(height = 70.dp, width = 70.dp),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = modifier.width(10.dp))
            Column{
                Text(
                    text = country.name.common,
                    style = MaterialTheme.typography.titleLarge,
                    //color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    country.region?.let {
                        Text(
                            text = "Region: $it",
                            //color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = modifier.width(10.dp))
                    country.population?.let {
                        Text(
                            text = "Population: $it",
                            //color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
        }
    }
}

//@Composable
//@Preview(apiLevel = 34, device = Devices.PIXEL_4)
//fun CountryTilePreview(){
//    MaterialTheme{
//        val flag = Flags(
//            png = "",
//            svg = "",
//            alt = ""
//        )
//        val name = Name(
//            common = "Nigeria",
//            official = "",
//            nativeName = mapOf()
//        )
//        val capital = listOf<String>()
//        CountryTile(
//            onClick = {},
//            country = Country(
//                flags = flag,
//                name = name,
//                population = 7484904,
//                region = "Africa",
//                subregion = "Eastern Africa",
//                borders = listOf("",""),
//                cca2 = "",
//                cca3 = "",
//                capital = capital
//                )
//        )
//
//    }
//}
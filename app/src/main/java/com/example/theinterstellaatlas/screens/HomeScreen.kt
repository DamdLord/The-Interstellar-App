package com.example.theinterstellaatlas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.theinterstellaatlas.AppViewModel
import com.example.theinterstellaatlas.Screens
import com.example.theinterstellaatlas.UiState
import com.example.theinterstellaatlas.components.CountryListDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    uiState: UiState,
    navController: NavController
){

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val regionList = listOf("Region","Africa", "Americas", "Asia", "Europe", "Oceania", "Antarctic")
    var chosenRegion by rememberSaveable { mutableStateOf(regionList[0]) }
    var searchingByRegion by rememberSaveable { mutableStateOf(false) }
    val countries = uiState.countries

    if(countries.isEmpty()){
        LaunchedEffect(Unit) {
            appViewModel.getAllCountries()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                    "The Interstellar Atlas",
                        fontWeight = FontWeight.Bold
                    )
                },
            )
        }
    ) { innerPadding->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Box(modifier.fillMaxWidth()){
                    Column {
                        Row(
                            modifier = modifier.fillMaxWidth(),

                        ) {
                            OutlinedTextField(
                                value = uiState.countrySearched,
                                onValueChange = {
                                    appViewModel.updateCountrySearched(it)
                                    appViewModel.updateUiStateCurrentCountry(it)
                                    appViewModel.updateCityToLookFor(it)
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(15.dp),
                                modifier = modifier.width(250.dp),
                                placeholder = {Text("Search for a country", color = MaterialTheme.colorScheme.onBackground)},
                                colors = OutlinedTextFieldDefaults.colors(MaterialTheme.colorScheme.onBackground),
                                keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done)
                            )
                            Spacer(modifier = modifier.width(20.dp))
                            Button(
                                onClick = {
                                    appViewModel.getCountriesByName()
                                },
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                            ) {
                                Text(
                                    text = "Search",
                                    color = MaterialTheme.colorScheme.onPrimary,

                                )
                            }

                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            ExposedDropdownMenuBox(
                                expanded = isExpanded,
                                onExpandedChange = { isExpanded = !isExpanded}
                            ) {
                                OutlinedTextField(
                                    value = uiState.regionSelected.ifEmpty {
                                        chosenRegion
                                    },
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {  },
                                    shape = RoundedCornerShape(15.dp),
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                                    },
                                    modifier = modifier
                                        .width(150.dp)
                                        .menuAnchor(),
                                    //colors = OutlinedTextFieldDefaults.colors(MaterialTheme.colorScheme.onPrimary)
                                )

                                ExposedDropdownMenu(
                                    expanded = isExpanded,
                                    onDismissRequest = { isExpanded = false }
                                ) {
                                    regionList.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option) },
                                            onClick = {
                                                isExpanded = false
                                                chosenRegion = option
                                                searchingByRegion = true
                                                appViewModel.updateRegionSelected(option)
                                                appViewModel.updateUiStateRegion(option)
                                                appViewModel.getCountriesByRegion()
                                            }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = modifier.width(10.dp))
                            Button(
                                onClick = {
                                    searchingByRegion = false
                                    appViewModel.getAllCountries()
                                },
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                            ){
                                Text(
                                    text = "Get all countries",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                        }
                    }
                }
                CountryListDisplay(
                    countries = countries,
                    onClick = {
                        navController.navigate(Screens.DETAILS.name)

                    },
                    appViewModel = appViewModel,
                    uiState = uiState,
                    onRetryClick = {
                        if (searchingByRegion){
                            appViewModel.getCountriesByRegion()
                        }else{
                            appViewModel.getAllCountries()
                        }
                    }
                )
//                if (uiState.hasWorked) {
//                    Row(
//                        modifier = modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Absolute.SpaceBetween
//                    ) {
//                        Button(
//                            onClick = { },
//                            shape = RoundedCornerShape(15.dp),
//                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
//                            enabled = !isFirstSet
//                        ) {
//                            Text(
//                                text = "Previous",
//                                color = MaterialTheme.colorScheme.onPrimary,
//                            )
//                        }
//                        Button(
//                            onClick = { isFirstSet = false },
//                            shape = RoundedCornerShape(15.dp),
//                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
//                        ) {
//                            Text(
//                                text = "Next",
//                                color = MaterialTheme.colorScheme.onPrimary,
//                            )
//                        }
//                    }
//                }
            }
        }
    }
}
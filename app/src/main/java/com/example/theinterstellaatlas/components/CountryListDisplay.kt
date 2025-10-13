package com.example.theinterstellaatlas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.theinterstellaatlas.AppViewModel
import com.example.theinterstellaatlas.R
import com.example.theinterstellaatlas.UiState
import com.example.theinterstellaatlas.retrofitAndApi.Country

@Composable
fun CountryListDisplay(
    modifier: Modifier = Modifier,
    countries: List<Country>,
    appViewModel: AppViewModel,
    uiState: UiState,
    onClick: () -> Unit,
    onRetryClick: () -> Unit,
){
    if (!uiState.isLoading && uiState.hasWorked && countries.isNotEmpty()){
        LazyColumn(
        ) {
            items(countries.size){
                CountryTile(
                    country = countries[it],
                    onClick = onClick,
                    appViewModel = appViewModel
                )
            }
        }
    } else if (!uiState.hasWorked && !uiState.isLoading){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(8.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(modifier = modifier.fillMaxWidth()) {
                    Card(
                        colors = CardDefaults.cardColors(Color.Red),
                        modifier = modifier
                            .fillMaxHeight()
                            .width(20.dp),
                        shape = RoundedCornerShape(topEnd = 0.dp, bottomEnd = 0.dp)
                    ) {
                    }
                    Spacer(modifier = modifier.width(5.dp))
                    Text(
                        text = appViewModel.errorMessage ?: "Something went wrong",
                        modifier = modifier.padding(top = 38.dp, start = 10.dp),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Button(
                onClick = onRetryClick,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Retry",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

        }
    }else if (uiState.isLoading) {
       for (i in 1..7){
           LoadingTile()
       }
    }
}


@Composable
fun LoadingTile(
    modifier: Modifier = Modifier
){
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
           // horizontalArrangement = Arrangement.,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ashflagplaceholder),
                contentDescription = null,
                modifier = modifier.size(height = 70.dp, width = 80.dp),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = modifier.width(10.dp))
            Column{
                Image(
                    painter = painterResource(R.drawable.ashtextholder),
                    contentDescription = null,
                    modifier = modifier.size(height = 10.dp, width = 100.dp),
                    contentScale = ContentScale.FillBounds,
                )
                Spacer(modifier = modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(R.drawable.ashtextholder),
                        contentDescription = null,
                        modifier = modifier.size(height = 10.dp, width = 100.dp),
                        contentScale = ContentScale.FillBounds,
                    )
                    Spacer(modifier = modifier.width(10.dp))
                    Image(
                        painter = painterResource(R.drawable.ashtextholder),
                        contentDescription = null,
                        modifier = modifier.size(height = 10.dp, width = 100.dp),
                        contentScale = ContentScale.FillBounds,
                    )

                }
            }
        }
    }

}

@Composable
@Preview(apiLevel = 34, device = Devices.PIXEL_4)
fun SHow(){
    MaterialTheme{
        LoadingTile(
        )
    }
}
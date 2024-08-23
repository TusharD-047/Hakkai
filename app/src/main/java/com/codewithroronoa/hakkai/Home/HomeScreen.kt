package com.codewithroronoa.hakkai.Home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.codewithroronoa.hakkai.data.remote.responses.RankAnime
import com.codewithroronoa.hakkai.data.remote.responses.Seasonal
import com.codewithroronoa.hakkai.utils.Resource
import timber.log.Timber
import java.time.LocalDate
import java.util.Calendar

fun getCurrentAnimeSeason(): String {
    // Get the current month
    val currentMonth = LocalDate.now().monthValue

    // Map month to anime season
    return when (currentMonth) {
        12, 1, 2 -> "winter"
        3, 4, 5 -> "spring"
        6, 7, 8 -> "summer"
        9, 10, 11 -> "fall"
        else -> "Unknown" // Should not occur
    }
}

@Composable
fun HomeScreen(
){
    Column(modifier = Modifier
        .background(Color.Black)
        .padding(8.dp)
        .fillMaxSize()){
        SeasonalAnime()
        TopAnime()
    }
}


@Composable
fun SeasonalAnime(
    homeViewModel: HomeViewModel = hiltViewModel()
){
    val season = getCurrentAnimeSeason()
    val y = LocalDate.now().year
    val order : String  = "Descending"
    Timber.tag("return").e(season)
    Timber.tag("return").e(y.toString())
    val seasonal_animes = produceState<Resource<Seasonal>>(initialValue = Resource.Loading()) {
        value = homeViewModel.getSeasonalAnime(y,season,order)
    }.value

    when(seasonal_animes){
        is Resource.Error -> {
            seasonal_animes.message?.let { Text(text = it, modifier = Modifier.padding(16.dp)) }
        }
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
        }
        is Resource.Success -> {
            val animeList = seasonal_animes.data!!.data

            Column(modifier = Modifier.padding(top = 16.dp).background(Color(0xff282C35), shape = RoundedCornerShape(16.dp))){
                Text(
                    text = "Top 10 Anime this Season",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = Color.White
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp)
                        .padding(bottom = 8.dp)
                ) {
                    item {
                        animeList.forEachIndexed { index, data ->
                            Box(
                                modifier = Modifier.padding(4.dp),
                                contentAlignment = Alignment.TopStart
                            ) {
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(data.node.main_picture.medium)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = data.node.title,
                                    modifier = Modifier.size(140.dp, 140.dp),
                                    contentScale = ContentScale.FillBounds,
                                    loading = {
                                        CircularProgressIndicator(
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.scale(0.5f)
                                        )
                                    }
                                )
                                Text(
                                    text = data.node.title,
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .width(140.dp)
                                        .align(Alignment.BottomCenter)
                                        .padding(4.dp),
                                    textAlign = TextAlign.Start,
                                    lineHeight = 10.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )

                            }
                        }
                    }
                }
            }
        }
    }

}@Composable
fun TopAnime(
    homeViewModel: HomeViewModel = hiltViewModel()
){
    val season = getCurrentAnimeSeason()
    val y = LocalDate.now().year
    val order : String  = "Descending"
    Timber.tag("return").e(season)
    Timber.tag("return").e(y.toString())
    val seasonal_animes = produceState<Resource<RankAnime>>(initialValue = Resource.Loading()) {
        value = homeViewModel.getTopAnime(rank = "all")
    }.value

    when(seasonal_animes){
        is Resource.Error -> {
            seasonal_animes.message?.let { Text(text = it, modifier = Modifier.padding(16.dp)) }
        }
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
        }
        is Resource.Success -> {
            val animeList = seasonal_animes.data!!.data

            Column(modifier = Modifier
                .padding(top = 16.dp)
                .background(Color(0xff282C35), shape = RoundedCornerShape(16.dp)),
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Top 10 Anime this Season",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = Color.White
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp)
                        .padding(bottom = 8.dp)
                ) {
                    item {
                        animeList.forEachIndexed { index, data ->
                            Box(
                                modifier = Modifier.padding(4.dp),
                                contentAlignment = Alignment.TopStart
                            ) {
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(data.node.main_picture.medium)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = data.node.title,
                                    modifier = Modifier.size(140.dp, 140.dp),
                                    contentScale = ContentScale.FillBounds,
                                    loading = {
                                        CircularProgressIndicator(
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.scale(0.5f)
                                        )
                                    }
                                )
                                Text(
                                    text = data.node.title,
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .width(140.dp)
                                        .align(Alignment.BottomCenter)
                                        .padding(4.dp),
                                    textAlign = TextAlign.Start,
                                    lineHeight = 10.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )

                            }
                        }
                    }
                }
            }
        }
    }

}
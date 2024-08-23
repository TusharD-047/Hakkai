package com.codewithroronoa.hakkai.Home

import androidx.lifecycle.ViewModel
import com.codewithroronoa.hakkai.Repository.HakkaiRepository
import com.codewithroronoa.hakkai.data.remote.responses.RankAnime
import com.codewithroronoa.hakkai.data.remote.responses.Seasonal
import com.codewithroronoa.hakkai.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HakkaiRepository
) : ViewModel(){
    suspend fun getSeasonalAnime(year : Int, season : String,order: String) : Resource<Seasonal>{
        return repository.getSeasonalList(year,season,order)
    }
    suspend fun getTopAnime(rank : String) : Resource<RankAnime>{
        return repository.getTopAnime(rank)
    }
}
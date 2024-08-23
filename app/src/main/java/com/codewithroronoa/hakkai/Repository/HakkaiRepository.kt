package com.codewithroronoa.hakkai.Repository

import com.codewithroronoa.hakkai.data.remote.MAL
import com.codewithroronoa.hakkai.data.remote.responses.RankAnime
import com.codewithroronoa.hakkai.data.remote.responses.Season
import com.codewithroronoa.hakkai.data.remote.responses.Seasonal
import com.codewithroronoa.hakkai.utils.Constants.CLIENT_ID
import com.codewithroronoa.hakkai.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import kotlin.math.truncate

@ActivityScoped
class HakkaiRepository @Inject constructor(
    private val mal: MAL
) {
    suspend fun getSeasonalList(year : Int, season : String,order : String) : Resource<Seasonal>{
        val response = try {
            mal.topSeasonalAnime(year,season,order,CLIENT_ID)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getTopAnime(rankType : String) : Resource<RankAnime>{
        val response = try{
            mal.topRankingAnime(rankType, CLIENT_ID)
        } catch (e : Exception){
            return Resource.Error(e.message.toString())
        }
        return  Resource.Success(response)
    }
}
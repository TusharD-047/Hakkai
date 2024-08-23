package com.codewithroronoa.hakkai.data.remote

import com.codewithroronoa.hakkai.data.remote.responses.RankAnime
import com.codewithroronoa.hakkai.data.remote.responses.Season
import com.codewithroronoa.hakkai.data.remote.responses.Seasonal
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MAL {
    @GET("anime/season/{year}/{season}")
    suspend fun topSeasonalAnime(
        @Path("year") year : Int,
        @Path("season") season : String,
        @Query("anime_num_list_users") order : String,
        @Header("X-MAL-CLIENT-ID") value: String
    ) : Seasonal

    @GET("anime/ranking")
    suspend fun topRankingAnime(
        @Query("ranking_type") ranking : String,
        @Header("X-MAL-CLIENT-ID") value: String
    ) : RankAnime

}
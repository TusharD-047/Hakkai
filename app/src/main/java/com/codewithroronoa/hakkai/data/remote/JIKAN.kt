package com.codewithroronoa.hakkai.data.remote

import com.codewithroronoa.hakkai.data.remote.responses.Schedule
import retrofit2.http.GET
import retrofit2.http.Query

interface JIKAN {

    @GET("schedules")
    suspend fun getSchedules(
        @Query("filter") day : String
    ): Schedule
}
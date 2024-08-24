package com.codewithroronoa.hakkai.Repository

import com.codewithroronoa.hakkai.data.remote.JIKAN
import com.codewithroronoa.hakkai.data.remote.responses.Schedule
import com.codewithroronoa.hakkai.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.security.PrivateKey
import javax.inject.Inject

@ActivityScoped
class JikanRepository @Inject constructor(
    private val jikan: JIKAN
){
    suspend fun getScheduleDay(day : String) : Resource<Schedule>{
        val response = try {
            jikan.getSchedules(day)
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }
}
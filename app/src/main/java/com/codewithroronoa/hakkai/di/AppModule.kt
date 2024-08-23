package com.codewithroronoa.hakkai.di

import com.codewithroronoa.hakkai.Repository.HakkaiRepository
import com.codewithroronoa.hakkai.data.remote.MAL
import com.codewithroronoa.hakkai.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHakkaiRepository(
        api : MAL)
    = HakkaiRepository(api)

    @Singleton
    @Provides
    fun provideApi() : MAL{
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MAL::class.java)
    }
}
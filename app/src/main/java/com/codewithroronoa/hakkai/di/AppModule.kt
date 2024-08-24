package com.codewithroronoa.hakkai.di

import com.codewithroronoa.hakkai.Repository.HakkaiRepository
import com.codewithroronoa.hakkai.Repository.JikanRepository
import com.codewithroronoa.hakkai.data.remote.JIKAN
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
        api : MAL,
    )
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

    @Singleton
    @Provides
    fun provideJikanRepository(
        api1 : JIKAN
    ) = JikanRepository(api1)

    @Singleton
    @Provides
    fun provideJApi() : JIKAN{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JIKAN::class.java)
    }
}
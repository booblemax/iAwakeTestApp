package com.example.iawaketestapp.di

import com.example.iawaketestapp.data.repository.MediaRepositoryImpl
import com.example.iawaketestapp.data.service.MediaService
import com.example.iawaketestapp.domain.repository.MediaRepository
import com.example.iawaketestapp.ui.base.MusicStateManager
import com.example.iawaketestapp.util.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun provideOkHttp() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { message ->
                Timber.tag("OkHttp").i(message)
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideMediaService(retrofit: Retrofit): MediaService = retrofit.create(MediaService::class.java)

    @Singleton
    @Provides
    fun provideMediaRepository(service: MediaService): MediaRepository = MediaRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideMusicStateManager() = MusicStateManager()
}
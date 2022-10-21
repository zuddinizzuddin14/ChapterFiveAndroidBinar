package com.example.mymovieapp.di

import com.example.mymovieapp.BuildConfig
import com.example.mymovieapp.data.server.services.ApiHelper
import com.example.mymovieapp.data.server.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun authInterceptor(): Interceptor {
        return Interceptor {
            val originRequest = it.request()
            val newUrl = originRequest.url.newBuilder().apply {
                addQueryParameter("api_key", BuildConfig.API_KEY)
            }.build()
            it.proceed(originRequest.newBuilder().url(newUrl).build())
        }
    }

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideApiHelper(apiService: ApiService): ApiHelper {
        return ApiHelper(apiService)
    }

}
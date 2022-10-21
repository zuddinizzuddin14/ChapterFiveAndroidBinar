package com.example.mymovieapp.di

import android.content.Context
import com.example.mymovieapp.data.local.datastore.UserDataStore
import com.example.mymovieapp.data.repositories.MovieRepository
import com.example.mymovieapp.data.repositories.UserRepository
import com.example.mymovieapp.data.server.services.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        apiHelper: ApiHelper
    ): MovieRepository = MovieRepository(apiHelper)

    @Singleton
    @Provides
    fun provideUserRepository(
        @ApplicationContext context : Context
    ): UserRepository = UserDataStore(context)

}
package com.example.mymovieapp.di

import com.example.mymovieapp.data.repositories.FirebaseAuthRepository
import com.example.mymovieapp.data.repositories.MovieRepository
import com.example.mymovieapp.data.server.firebase.FirebaseAuthRepositoryImpl
import com.example.mymovieapp.data.server.services.ApiHelper
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuthRepository(
        firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl
    ): FirebaseAuthRepository = firebaseAuthRepositoryImpl
}
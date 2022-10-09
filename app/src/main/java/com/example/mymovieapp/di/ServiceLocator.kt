package com.example.mymovieapp.di

import android.content.Context
import com.example.mymovieapp.data.local.database.AppDatabase
import com.example.mymovieapp.data.local.database.dao.UserDao
import com.example.mymovieapp.data.local.database.datasource.UserDataSource
import com.example.mymovieapp.data.local.database.datasource.UserDataSourceImpl
import com.example.mymovieapp.data.local.preference.UserPreference
import com.example.mymovieapp.data.local.preference.UserPreferenceDataSource
import com.example.mymovieapp.data.local.preference.UserPreferenceDataSourceImpl
import com.example.mymovieapp.data.repositories.MovieRepository
import com.example.mymovieapp.data.repositories.MovieRepositoryImpl
import com.example.mymovieapp.data.repositories.UserRepository
import com.example.mymovieapp.data.repositories.UserRepositoryImpl
import com.example.mymovieapp.data.server.services.TmdbApiService

object ServiceLocator {

    fun provideMovieRepository(): MovieRepository {
        return MovieRepositoryImpl(
            TmdbApiService.invoke()
        )
    }

    fun provideAppDatabase(appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    fun provideUserPreference(context: Context): UserPreference {
        return UserPreference(context)
    }

    fun provideUserPreferenceDataSource(context: Context): UserPreferenceDataSource {
        return UserPreferenceDataSourceImpl(provideUserPreference(context))
    }

    fun provideUserDao(appContext: Context): UserDao {
        return provideAppDatabase(appContext).userDao()
    }

    fun provideUserDataSource(appContext: Context): UserDataSource {
        return UserDataSourceImpl(provideUserDao(appContext))
    }

    fun provideUserRepository(context: Context): UserRepository {
        return UserRepositoryImpl(
            provideUserDataSource(context),
            provideUserPreferenceDataSource(context)
        )
    }

}
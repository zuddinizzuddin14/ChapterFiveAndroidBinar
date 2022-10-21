package com.example.mymovieapp.data.repositories

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun setSession(session: Boolean)

    suspend fun setUser(username: String, password: String, name: String)

    suspend fun setPassword(password: String)

    suspend fun setName(name: String)

    fun getSession(): Flow<Boolean>

    fun getUsername(): Flow<String>

    fun getPassword(): Flow<String>

    fun getName(): Flow<String>

}
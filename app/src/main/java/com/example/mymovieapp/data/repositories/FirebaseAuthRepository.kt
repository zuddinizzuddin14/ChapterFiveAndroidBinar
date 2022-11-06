package com.example.mymovieapp.data.repositories

import android.graphics.Bitmap
import com.example.mymovieapp.wrapper.Resource
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthRepository {

    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    suspend fun register(email: String, password: String, name: String): Resource<FirebaseUser>

    suspend fun changePassword(password: String): Resource<FirebaseUser>

    suspend fun updateProfile(name: String): Resource<FirebaseUser>

    fun logout()
}
package com.example.mymovieapp.data.server.firebase

import com.example.mymovieapp.data.repositories.FirebaseAuthRepository
import com.example.mymovieapp.utils.await
import com.example.mymovieapp.wrapper.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun register(email: String, password: String, name: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(userProfileChangeRequest { displayName = name })?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun changePassword(password: String): Resource<FirebaseUser> {
        return try {
            currentUser?.updatePassword(password)?.await()
            Resource.Success(currentUser!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun updateProfile(
        name: String
    ): Resource<FirebaseUser> {
        return try {
            currentUser?.updateProfile(userProfileChangeRequest {
                displayName = name
//                photoUri = photo
            })?.await()
            Resource.Success(currentUser!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
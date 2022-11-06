package com.example.mymovieapp.data.repositories

import com.example.mymovieapp.data.server.firebase.FirebaseAuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FirebaseAuthRepositoryImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseAuthRepository: FirebaseAuthRepository
    private lateinit var firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl

    @Before
    fun setUp() {
        firebaseAuth = mockk()
        firebaseAuthRepository = mockk()
        firebaseAuthRepositoryImpl = FirebaseAuthRepositoryImpl(firebaseAuth)
    }

    @Test
    fun getCurrentUser() {
        val currentUser = mockk<FirebaseUser>()

        every {
            runBlocking {
                firebaseAuth.currentUser
            }
        }  returns currentUser

        firebaseAuthRepositoryImpl.currentUser

        verify {
            runBlocking {
                firebaseAuth.currentUser
            }
        }
    }

}
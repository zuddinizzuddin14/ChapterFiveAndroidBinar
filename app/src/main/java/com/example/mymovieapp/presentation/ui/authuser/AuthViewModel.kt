package com.example.mymovieapp.presentation.ui.authuser

import androidx.lifecycle.ViewModel
import com.example.mymovieapp.data.repositories.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
): ViewModel() {

    val currentUser: FirebaseUser?
        get() = repository.currentUser

}
package com.example.mymovieapp.presentation.ui.profileuser

import androidx.lifecycle.ViewModel
import com.example.mymovieapp.data.repositories.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
    ): ViewModel() {

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    fun logout() {
        repository.logout()
    }

}
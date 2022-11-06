package com.example.mymovieapp.presentation.ui.profileuser.subprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.repositories.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
): ViewModel() {

    fun changePassword(password: String) {
        viewModelScope.launch {
            repository.changePassword(password)
        }
    }

}
package com.example.mymovieapp.presentation.ui.loginuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
    ): ViewModel() {


    fun getUsername(): LiveData<String> {
        return repository.getUsername().asLiveData()
    }

    fun getPassword(): LiveData<String> {
        return repository.getPassword().asLiveData()
    }

    fun sessionGranted() {
        viewModelScope.launch {
            repository.setSession(true)
        }
    }

}
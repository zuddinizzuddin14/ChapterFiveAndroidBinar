package com.example.mymovieapp.presentation.ui.profileuser.subprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    fun getPassword(): LiveData<String> {
        return repository.getPassword().asLiveData()
    }

    fun changePassword(password: String) {
        viewModelScope.launch {
            repository.setPassword(password)
        }
    }

}
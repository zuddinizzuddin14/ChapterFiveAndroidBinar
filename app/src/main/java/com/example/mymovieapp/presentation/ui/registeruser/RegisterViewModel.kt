package com.example.mymovieapp.presentation.ui.registeruser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
    ): ViewModel() {

//    private val loadingState = MutableLiveData<Boolean>()

    fun registerUser(username: String, password: String, name: String) {
        viewModelScope.launch {
            repository.setUser(username, password, name)
        }
    }

//    fun registerUser(item: UserEntity) {
//        loadingState.postValue(true)
//        viewModelScope.launch {
//            registerResult.postValue(repository.insertUser(item))
//            loadingState.postValue(false)
//        }
//    }

}
package com.example.mymovieapp.presentation.ui.registeruser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.local.database.entity.UserEntity
import com.example.mymovieapp.data.repositories.UserRepository
import com.example.mymovieapp.wrapper.Resource
import kotlinx.coroutines.launch

class RegisterViewModel (private val repository: UserRepository): ViewModel() {

    private val registerResult = MutableLiveData<Resource<Number>>()
    private val loadingState = MutableLiveData<Boolean>()

    fun checkIsUsernameExist(username: String): Boolean {
        return repository.checkIsUsernameExist(username)
    }

    fun registerUser(item: UserEntity) {
        loadingState.postValue(true)
        viewModelScope.launch {
            registerResult.postValue(repository.insertUser(item))
            loadingState.postValue(false)
        }
    }

}
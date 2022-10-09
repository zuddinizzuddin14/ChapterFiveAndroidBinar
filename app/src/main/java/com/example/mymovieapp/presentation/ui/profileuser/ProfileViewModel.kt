package com.example.mymovieapp.presentation.ui.profileuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.local.database.entity.UserEntity
import com.example.mymovieapp.data.repositories.UserRepository
import com.example.mymovieapp.wrapper.Resource
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    val userResult = MutableLiveData<Resource<UserEntity?>>()
    val changePasswordResult = MutableLiveData<Resource<Number>>()
    val loadingState = MutableLiveData<Boolean>()

    fun userResult() {
        val userId = repository.getUserId()
        viewModelScope.launch {
            userResult.postValue(repository.getUserById(userId))
        }
    }

    fun logout() {
        repository.setUserLogin(false, -1)
    }



    fun changePassword(item: UserEntity) {
        loadingState.postValue(true)
        viewModelScope.launch {
            changePasswordResult.postValue(repository.updateUser(item))
            loadingState.postValue(true)
        }
    }

}
package com.example.mymovieapp.presentation.ui.loginuser

import androidx.lifecycle.ViewModel
import com.example.mymovieapp.data.repositories.UserRepository

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    fun checkIsUserCorrect(username: String, password: String): Boolean {
        return repository.checkIsUserCorrect(username, password)
    }

    fun getIdUser(username: String, password: String): Int {
        return repository.getUserId(username, password)
    }

    fun setUserLogin(newSession: Boolean, newUserId: Int) {
        return repository.setUserLogin(newSession, newUserId)
    }

}
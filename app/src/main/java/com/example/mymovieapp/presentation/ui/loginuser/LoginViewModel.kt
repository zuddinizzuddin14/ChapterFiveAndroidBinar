package com.example.mymovieapp.presentation.ui.loginuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.repositories.FirebaseAuthRepository
import com.example.mymovieapp.wrapper.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
): ViewModel() {

    val loginUser = MutableLiveData<Resource<FirebaseUser>?>()
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()

    fun loginEmailPassword(email: String, password: String) {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
                val data = repository.login(email, password)
                viewModelScope.launch(Dispatchers.Main) {
                    loginUser.value = data
                    loadingState.postValue(false)
                    errorState.postValue(Pair(false, null))
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    loadingState.postValue(false)
                    errorState.postValue(Pair(true, e))
                }
            }
        }
    }

}
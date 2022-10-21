package com.example.mymovieapp.presentation.ui.profileuser

import androidx.lifecycle.*
import com.example.mymovieapp.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
    ): ViewModel() {

//    val userResult = MutableLiveData<Resource<UserEntity?>>()
//    val changePasswordResult = MutableLiveData<Resource<Number>>()
//    val loadingState = MutableLiveData<Boolean>()

    fun nameResult(): LiveData<String> {
        return repository.getName().asLiveData()
    }

    fun sessionDestroy() {
        viewModelScope.launch {
            repository.setSession(false)
        }
    }

}
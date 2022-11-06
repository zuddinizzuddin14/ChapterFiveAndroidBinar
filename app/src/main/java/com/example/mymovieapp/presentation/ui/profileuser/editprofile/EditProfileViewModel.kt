package com.example.mymovieapp.presentation.ui.profileuser.editprofile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.mymovieapp.data.repositories.FirebaseAuthRepository
import com.example.mymovieapp.utils.workers.BlurWorker
import com.example.mymovieapp.wrapper.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
): ViewModel() {

    val userData = MutableLiveData<Resource<FirebaseUser>>()
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    fun getImage(): Uri? {
        return repository.currentUser?.photoUrl
    }

    fun getName(): String? {
        return repository.currentUser?.displayName
    }

    fun saveImage(image: Bitmap) {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
//                val data = repository.uploadPhoto(image)
                viewModelScope.launch(Dispatchers.Main) {
//                    userData.value = data
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

    fun changeName(name: String) {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            errorState.postValue(Pair(false, null))
            try {
//                val data = repository.editProfile(name)
                viewModelScope.launch(Dispatchers.Main) {
//                    userData.value = data
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

//    fun blurImage(context: Context, image: String) {
//
//        val workManager = WorkManager.getInstance(context)
//
//        val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
//        blurBuilder.setInputData(inputBitmap(image))
//
//        var continuation = workManager
//            .beginWith(blurBuilder.build())
//
//        val constraints = Constraints.Builder()
//            .setRequiresCharging(true)
//            .build()
//
//        val save = OneTimeWorkRequestBuilder<SaveWorker>()
//            .setConstraints(constraints)
//            .addTag(IMAGE_OUTPUT)
//            .build()
//
//        continuation = continuation.then(save)
//
//        continuation.enqueue()
//    }

    fun blurImage(context: Context) {
        val constraints: Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val myWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(BlurWorker::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(myWorkRequest)
    }
}
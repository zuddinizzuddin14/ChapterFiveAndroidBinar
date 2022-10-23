package com.example.mymovieapp.presentation.ui.profileuser.editprofile

import android.content.Context
import androidx.lifecycle.*
import androidx.work.*
import com.example.mymovieapp.data.repositories.UserRepository
import com.example.mymovieapp.utils.workers.BlurWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    fun getImage(): LiveData<String> {
        return repository.getImage().asLiveData()
    }

    fun getName(): LiveData<String> {
        return repository.getName().asLiveData()
    }

    fun saveImage(image: String) {
        viewModelScope.launch {
            repository.setImage(image)
        }
    }

    fun changeName(name: String) {
        viewModelScope.launch {
            repository.setName(name)
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
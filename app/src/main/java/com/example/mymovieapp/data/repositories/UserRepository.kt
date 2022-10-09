package com.example.mymovieapp.data.repositories

import com.example.mymovieapp.data.local.database.datasource.UserDataSource
import com.example.mymovieapp.data.local.database.entity.UserEntity
import com.example.mymovieapp.data.local.preference.UserPreferenceDataSource
import com.example.mymovieapp.wrapper.Resource

interface UserRepository {

    fun checkIsUsernameExist(username: String): Boolean
    fun checkIsUserCorrect(username: String, password: String): Boolean
    fun getUserId(username: String, password: String): Int
    suspend fun getUserById(userId: Int): Resource<UserEntity?>
    suspend fun insertUser(user: UserEntity): Resource<Number>
    suspend fun updateUser(user: UserEntity): Resource<Number>

    fun getSession(): Boolean
    fun getUserId(): Int
    fun setUserLogin(newSession: Boolean, newUserId: Int)

}

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val userPreferenceDataSource: UserPreferenceDataSource
): UserRepository {
    override fun checkIsUsernameExist(username: String): Boolean {
        return userDataSource.getUsernameBoolean(username)
    }

    override fun checkIsUserCorrect(username: String, password: String): Boolean {
        return userDataSource.getUserBoolean(username, password)
    }

    override fun getUserId(username: String, password: String): Int {
        return userDataSource.getUserId(username, password)
    }

    override suspend fun getUserById(userId: Int): Resource<UserEntity?> {
        return proceed {
            userDataSource.getUserById(userId)
        }
    }

    override suspend fun insertUser(user: UserEntity): Resource<Number> {
        return proceed {
            userDataSource.insertUser(user)
        }
    }

    override suspend fun updateUser(user: UserEntity): Resource<Number> {
        return proceed {
            userDataSource.updateUser(user)
        }
    }

    override fun getSession(): Boolean {
        return userPreferenceDataSource.getSession()
    }

    override fun getUserId(): Int {
        return userPreferenceDataSource.getUserId()
    }

    override fun setUserLogin(newSession: Boolean, newUserId: Int) {
        userPreferenceDataSource.setSession(newSession)
        userPreferenceDataSource.setUserId(newUserId)
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}
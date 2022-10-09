package com.example.mymovieapp.data.local.preference

interface UserPreferenceDataSource {

    fun getSession(): Boolean
    fun getUserId(): Int
    fun setSession(newSession: Boolean)
    fun setUserId(newUserId: Int)

}

class UserPreferenceDataSourceImpl(
    private val userPreference: UserPreference
    ) : UserPreferenceDataSource {
    override fun getSession(): Boolean {
        return userPreference.session
    }

    override fun getUserId(): Int {
        return userPreference.userId
    }

    override fun setSession(newSession: Boolean) {
        userPreference.session = newSession
    }

    override fun setUserId(newUserId: Int) {
        userPreference.userId = newUserId
    }

}
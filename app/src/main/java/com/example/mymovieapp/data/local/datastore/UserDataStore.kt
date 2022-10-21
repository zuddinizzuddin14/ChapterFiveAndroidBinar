package com.example.mymovieapp.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mymovieapp.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val context: Context
): UserRepository {

    override suspend fun setUser(username: String, password: String, name: String) {
        context.userDataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[PASSWORD_KEY] = password
            preferences[NAME_KEY] = name
        }
    }

    override suspend fun setPassword(password: String) {
        context.userDataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = password
        }
    }

    override suspend fun setName(name: String) {
        context.userDataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    override suspend fun setSession(session: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[SESSION_KEY] = session
        }
    }

    override fun getUsername(): Flow<String> {
        return context.userDataStore.data.map { preferences ->
            preferences[USERNAME_KEY] ?: "null"
        }
    }

    override fun getPassword(): Flow<String> {
        return context.userDataStore.data.map { preferences ->
            preferences[PASSWORD_KEY] ?: "null"
        }
    }

    override fun getName(): Flow<String> {
        return context.userDataStore.data.map { preferences ->
            preferences[NAME_KEY] ?: "null"
        }    }

    override fun getSession(): Flow<Boolean> {
        return context.userDataStore.data.map { preferences ->
            preferences[SESSION_KEY] ?: false
        }
    }

    companion object {
        private const val DATASTORE_NAME = "user_preferences"

        private val SESSION_KEY = booleanPreferencesKey("session_key")
        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val PASSWORD_KEY = stringPreferencesKey("password_key")
        private val NAME_KEY = stringPreferencesKey("name_key")

        private val Context.userDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

}
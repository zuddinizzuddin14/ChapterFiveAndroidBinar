package com.example.mymovieapp.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreference(context: Context) {

    private val preference: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = "user"
        private const val MODE = Context.MODE_PRIVATE
    }

    var session: Boolean
        get() = preference.getBoolean(PreferenceKey.PREF_USER_SESSION, false)
        set(value) = preference.edit() {
            this.putBoolean(PreferenceKey.PREF_USER_SESSION, value)
        }

    var userId: Int
        get() = preference.getInt(PreferenceKey.PREF_USER_ID, -1)
        set(value) = preference.edit {
            this.putInt(PreferenceKey.PREF_USER_ID, value)
        }


}

object PreferenceKey {
    val PREF_USER_SESSION = "PREF_USER_SESSION"
    val PREF_USER_ID = "PREF_USER_ID"
}
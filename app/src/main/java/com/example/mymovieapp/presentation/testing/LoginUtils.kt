package com.example.mymovieapp.presentation.testing

object LoginUtils {

    fun validateFormLogin(
        email: String,
        password: String,
    ): Boolean {
        var isFormValid = true
        if (email.isEmpty()) {
            isFormValid = false
        }
        if (password.length < 6) {
            isFormValid = false
        }
        if (password.isEmpty()) {
            isFormValid = false
        }
        return isFormValid
    }

}
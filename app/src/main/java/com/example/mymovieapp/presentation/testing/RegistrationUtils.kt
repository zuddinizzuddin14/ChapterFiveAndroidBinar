package com.example.mymovieapp.presentation.testing

object RegistrationUtils {

    fun validateFormRegister(
        email: String,
        password: String,
        confirmPassword: String,
        name: String
    ): Boolean {
        var isFormValid = true
        if (email.isEmpty()) {
            isFormValid = false
        }
        if (name.isEmpty()) {
            isFormValid = false
        }
        if (password.length < 6) {
            isFormValid = false
        }
        if (password.isEmpty()) {
            isFormValid = false
        }
        if (confirmPassword.isEmpty()) {
            isFormValid = false
        }
        if (password != confirmPassword) {
            isFormValid = false
        }
        return isFormValid
    }

}
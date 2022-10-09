package com.example.mymovieapp.presentation.ui.registeruser

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovieapp.R
import com.example.mymovieapp.data.local.database.entity.UserEntity
import com.example.mymovieapp.databinding.ActivityRegisterBinding
import com.example.mymovieapp.di.ServiceLocator
import com.example.mymovieapp.utils.viewModelFactory

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModelFactory {
        RegisterViewModel(ServiceLocator.provideUserRepository(this@RegisterActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun registerUser() {
        if (validateForm()) {
            if (validateUsername()) {
                viewModel.registerUser(parseFormIntoEntity())
                Toast.makeText(this@RegisterActivity, "Register Success", Toast.LENGTH_SHORT).show()
                onBackPressed()
            } else {
                Toast.makeText(this@RegisterActivity, getString(R.string.error_text_username_already), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        val name = binding.etName.text.toString()
        var isFormValid = true
        if (username.isEmpty()) {
            isFormValid = false
            binding.tilUsername.isErrorEnabled = true
            binding.tilUsername.error = getString(R.string.error_text_empty_username)
        } else {
            binding.tilUsername.isErrorEnabled = false
        }
        if (password.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.error_text_empty_password)
        } else {
            binding.tilPassword.isErrorEnabled = false
        }
        if (confirmPassword.isEmpty()) {
            isFormValid = false
            binding.tilConfirmPassword.isErrorEnabled = true
            binding.tilConfirmPassword.error = getString(R.string.error_text_empty_confirm_password)
        } else {
            binding.tilConfirmPassword.isErrorEnabled = false
        }
        if (password != confirmPassword) {
            isFormValid = false
            Toast.makeText(
                this,
                getString(R.string.error_text_password_not_match),
                Toast.LENGTH_SHORT
            ).show()
        }
        if (name.isEmpty()) {
            isFormValid = false
            binding.tilName.isErrorEnabled = true
            binding.tilName.error = getString(R.string.error_text_empty_name)
        } else {
            binding.tilName.isErrorEnabled = false
        }
        return isFormValid
    }

    private fun validateUsername(): Boolean {
        return viewModel.checkIsUsernameExist(binding.etUsername.text.toString().trim()).not()
    }

    private fun parseFormIntoEntity(): UserEntity {
        return UserEntity(
            username = binding.etUsername.text.toString().trim(),
            password = binding.etPassword.text.toString().trim(),
            name = binding.etName.text.toString().trim()
        )
    }


}
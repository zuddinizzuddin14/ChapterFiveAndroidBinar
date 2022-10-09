package com.example.mymovieapp.presentation.ui.profileuser.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.mymovieapp.R
import com.example.mymovieapp.data.local.database.entity.UserEntity
import com.example.mymovieapp.databinding.ActivityChangePasswordBinding
import com.example.mymovieapp.di.ServiceLocator
import com.example.mymovieapp.presentation.ui.profileuser.ProfileViewModel
import com.example.mymovieapp.utils.viewModelFactory

class ChangePasswordActivity : AppCompatActivity() {

    private val binding: ActivityChangePasswordBinding by lazy {
        ActivityChangePasswordBinding.inflate(layoutInflater)
    }

    private val viewModel: ProfileViewModel by viewModelFactory {
        ProfileViewModel(ServiceLocator.provideUserRepository(this@ChangePasswordActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        obServeData()
        setClickListeners()
    }

    private fun obServeData() {

//        viewModel.changePasswordResult.observe(this) {
//            changePassword(it.payload)
//        }
    }

    private fun changePassword() {
        if (validateForm()) {
//            viewModel.registerUser(parseFormIntoEntity())
//            Toast.makeText(this@RegisterActivity, "Register Success", Toast.LENGTH_SHORT).show()
//            onBackPressed()
        }
    }

    private fun validateForm(): Boolean {
        val password = binding.etPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()
        val confirmNewPassword = binding.etConfirmNewPassword.text.toString()
        var isFormValid = true
        if (password.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.error_text_empty_username)
        } else {
            binding.tilPassword.isErrorEnabled = false
        }
        if (newPassword.isEmpty()) {
            isFormValid = false
            binding.tilNewPassword.isErrorEnabled = true
            binding.tilNewPassword.error = getString(R.string.error_text_empty_password)
        } else {
            binding.tilNewPassword.isErrorEnabled = false
        }
        if (confirmNewPassword.isEmpty()) {
            isFormValid = false
            binding.tilConfirmNewPassword.isErrorEnabled = true
            binding.tilConfirmNewPassword.error = getString(R.string.error_text_empty_confirm_password)
        } else {
            binding.tilConfirmNewPassword.isErrorEnabled = false
        }
        if (newPassword != confirmNewPassword) {
            isFormValid = false
            Toast.makeText(
                this,
                getString(R.string.error_text_password_not_match),
                Toast.LENGTH_SHORT
            ).show()
        }
        return isFormValid
    }

//    private fun parseFormIntoEntity(): UserEntity {
//        return UserEntity(
//            id = ,
//            username = ,
//            password = binding.etPassword.text.toString().trim(),
//            name = binding.etName.text.toString().trim()
//        ).apply {
//            if (isEditAction()) {
//                noteId?.let {
//                    id = it
//                }
//            }
//        }
//    }

    private fun setClickListeners() {
        binding.btnSave.setOnClickListener {

        }
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        viewModel.userResult()
    }

}
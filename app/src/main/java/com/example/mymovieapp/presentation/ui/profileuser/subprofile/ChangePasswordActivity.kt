package com.example.mymovieapp.presentation.ui.profileuser.subprofile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.ActivityChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {

    private val binding: ActivityChangePasswordBinding by lazy {
        ActivityChangePasswordBinding.inflate(layoutInflater)
    }

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun changePassword() {
        if (validateForm()) {
            val newPassword = binding.etNewPassword.text.toString().trim()
            viewModel.changePassword(newPassword)
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.success_change_password), Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateForm(): Boolean {
        val newPassword = binding.etNewPassword.text.toString()
        val confirmNewPassword = binding.etConfirmNewPassword.text.toString()
        var isFormValid = true
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

    private fun setClickListeners() {
        binding.btnSave.setOnClickListener {
            changePassword()
        }
        binding.btnCancel.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}
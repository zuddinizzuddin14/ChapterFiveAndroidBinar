package com.example.mymovieapp.presentation.ui.loginuser

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.ActivityLoginBinding
import com.example.mymovieapp.presentation.ui.listmovie.MovieListActivity
import com.example.mymovieapp.presentation.ui.registeruser.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        if (validateForm()) {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            var isUsernameCorrect = false
            var isPasswordCorrect = false

            viewModel.getUsername().observe(this@LoginActivity) {
                if (username == it) isUsernameCorrect = true
            }

            viewModel.getPassword().observe(this@LoginActivity) {
                if (password == it) isPasswordCorrect = true
            }

            if(isUsernameCorrect && isPasswordCorrect){
                viewModel.sessionGranted()
                startActivity(Intent(this@LoginActivity, MovieListActivity::class.java))
            } else{
                Toast.makeText(this@LoginActivity, getString(R.string.error_text_login_not_correct), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
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
        return isFormValid
    }
}
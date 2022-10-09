package com.example.mymovieapp.presentation.ui.loginuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.ActivityLoginBinding
import com.example.mymovieapp.di.ServiceLocator
import com.example.mymovieapp.presentation.ui.listmovie.MovieListActivity
import com.example.mymovieapp.presentation.ui.registeruser.RegisterActivity
import com.example.mymovieapp.utils.viewModelFactory

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModelFactory {
        LoginViewModel(ServiceLocator.provideUserRepository(this@LoginActivity))
    }

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
            val isUserCorrect = viewModel.checkIsUserCorrect(username, password)
            if(isUserCorrect){
                val userId = viewModel.getIdUser(username, password)
                viewModel.setUserLogin(true, userId)
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
package com.example.mymovieapp.presentation.ui.profileuser

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovieapp.databinding.ActivityProfileBinding
import com.example.mymovieapp.presentation.ui.profileuser.changepassword.ChangePasswordActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        obServeData()
        setClickListeners()
    }

    private fun obServeData() {
        viewModel.nameResult().observe(this) {
            binding.tvProfileName.text = it.toString()
        }
    }

    private fun setClickListeners() {
        binding.llChangePassword.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, ChangePasswordActivity::class.java))
        }
        binding.llLogout.setOnClickListener {
            viewModel.sessionDestroy()
            onDestroy()
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}
package com.example.mymovieapp.presentation.ui.profileuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mymovieapp.data.local.database.entity.UserEntity
import com.example.mymovieapp.databinding.ActivityProfileBinding
import com.example.mymovieapp.di.ServiceLocator
import com.example.mymovieapp.presentation.ui.listmovie.MovieListActivity
import com.example.mymovieapp.presentation.ui.profileuser.changepassword.ChangePasswordActivity
import com.example.mymovieapp.utils.viewModelFactory

class ProfileActivity : AppCompatActivity() {

    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: ProfileViewModel by viewModelFactory {
        ProfileViewModel(ServiceLocator.provideUserRepository(this@ProfileActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        obServeData()
        setClickListeners()
    }

    private fun obServeData() {
        viewModel.userResult.observe(this) {
            bindDataToView(it.payload)
        }
    }

    private fun setClickListeners() {
        binding.llChangePassword.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, ChangePasswordActivity::class.java))
        }
        binding.llLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this@ProfileActivity, MovieListActivity::class.java))
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun bindDataToView(data: UserEntity?) {
        data?.let {
            binding.tvProfileName.text = data.name
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
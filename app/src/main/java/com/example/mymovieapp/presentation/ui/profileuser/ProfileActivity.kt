package com.example.mymovieapp.presentation.ui.profileuser

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mymovieapp.databinding.ActivityProfileBinding
import com.example.mymovieapp.presentation.ui.listmovie.MovieListActivity
import com.example.mymovieapp.presentation.ui.profileuser.editprofile.EditProfileActivity
import com.example.mymovieapp.presentation.ui.profileuser.subprofile.ChangePasswordActivity
import com.example.mymovieapp.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: ProfileViewModel by viewModels()

    private val imageUtil = ImageUtil()

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
        viewModel.imageResult().observe(this) {
            if (!it.equals("null")) {
                binding.ivProfile.load(imageUtil.stringToBitMap(it)) {
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.llEditProfile.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, EditProfileActivity::class.java))
        }
        binding.llChangePassword.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, ChangePasswordActivity::class.java))
        }
        binding.llLogout.setOnClickListener {
            viewModel.sessionDestroy()
            startActivity(Intent(this@ProfileActivity, MovieListActivity::class.java))
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}
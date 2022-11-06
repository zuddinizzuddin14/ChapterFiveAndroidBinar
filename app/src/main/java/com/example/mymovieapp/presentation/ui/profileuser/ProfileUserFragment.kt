package com.example.mymovieapp.presentation.ui.profileuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentProfileUserBinding
import com.example.mymovieapp.presentation.ui.authuser.SplashActivity
import com.example.mymovieapp.presentation.ui.profileuser.editprofile.EditProfileActivity
import com.example.mymovieapp.presentation.ui.profileuser.subprofile.ChangePasswordActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileUserFragment : Fragment() {

    private var _binding: FragmentProfileUserBinding? = null

    private val binding by lazy { _binding!! }

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    override fun onResume() {
        super.onResume()
        obServeData()
    }

    private fun obServeData() {
        val user = viewModel.currentUser
        if (user != null) {
            if (user.displayName.isNullOrEmpty().not()) {
                binding.tvProfileName.text = user.displayName
            }
            if (user.photoUrl.toString() != "null") {
                binding.ivProfile.load(user.photoUrl) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_account)
                }
            }
//            val profileUpdates = userProfileChangeRequest {
//                displayName = "Zuddin Izzuddin"
//                photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
//            }
//
//            userNow.updateProfile(profileUpdates)
//                .addOnCompleteListener { task ->
////                    if (task.isSuccessful) {
////                        Log.d(TAG, "User profile updated.")
////                    }
//                }
        } else {
            startActivity(Intent(context, SplashActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setClickListeners() {
        binding.llEditProfile.setOnClickListener {
            startActivity(Intent(context, EditProfileActivity::class.java))
        }
        binding.llChangePassword.setOnClickListener {
            startActivity(Intent(context, ChangePasswordActivity::class.java))
        }
        binding.llLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(context, SplashActivity::class.java))
            requireActivity().finish()
        }
    }

}
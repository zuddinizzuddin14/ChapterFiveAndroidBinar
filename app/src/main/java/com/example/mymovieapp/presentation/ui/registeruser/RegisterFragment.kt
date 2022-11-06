package com.example.mymovieapp.presentation.ui.registeruser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentRegisterBinding
import com.example.mymovieapp.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    private val binding by lazy { _binding!! }

    private lateinit var navController: NavController

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(view)
        setClickListeners()
        observeData()
    }

    private fun initList(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun observeData() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.pbRegister.isVisible = isLoading
        }
        viewModel.errorState.observe(viewLifecycleOwner) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }
        viewModel.registerUser.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Toast.makeText(context, getString(R.string.success_register), Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.action_registerFragment_to_loginFragment)
                    viewModel.logout()
                }
                null -> {}
            }
        }
    }

    private fun setClickListeners() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        binding.ivBack.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
        onBackPress()
    }

    private fun registerUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        val name = binding.etName.text.toString().trim()
        if (validateForm(email, password, confirmPassword, name)) {
            viewModel.registerEmailPassword(email, password, name)
        }
    }

    private fun validateForm(
        email: String,
        password: String,
        confirmPassword: String,
        name: String
    ): Boolean {
        var isFormValid = true
        if (email.isEmpty()) {
            isFormValid = false
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.error_text_empty_email)
        } else {
            binding.tilEmail.isErrorEnabled = false
        }
        if (name.isEmpty()) {
            isFormValid = false
            binding.tilName.isErrorEnabled = true
            binding.tilName.error = getString(R.string.error_text_empty_name)
        } else {
            binding.tilName.isErrorEnabled = false
        }
        if (password.length < 6) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilConfirmPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.error_text_length_password)
            binding.tilConfirmPassword.error = getString(R.string.error_text_length_password)
        } else {
            binding.tilPassword.isErrorEnabled = false
            binding.tilConfirmPassword.isErrorEnabled = false
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
                context,
                getString(R.string.error_text_password_not_match),
                Toast.LENGTH_SHORT
            ).show()
        }
        return isFormValid
    }

    private fun onBackPress() {
        val onBackPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
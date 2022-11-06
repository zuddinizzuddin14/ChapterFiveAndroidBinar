package com.example.mymovieapp.presentation.ui.loginuser

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentLoginBinding
import com.example.mymovieapp.presentation.ui.listmovie.MainActivity
import com.example.mymovieapp.wrapper.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var navController: NavController

    private var _binding: FragmentLoginBinding? = null

    private val binding by lazy { _binding!! }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(view)
        setClickListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.pbLogin.isVisible = isLoading
        }
        viewModel.errorState.observe(viewLifecycleOwner) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }
        viewModel.loginUser.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Toast.makeText(context, it.payload?.email, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, MainActivity::class.java))
                    requireActivity().finish()
                }
                null -> {}
            }
        }
    }

    private fun setClickListeners() {
        binding.btnLogin.setOnClickListener {
            loginAccount()
        }
        binding.btnLoginGoogle.setOnClickListener {
            signInGoogle.launch(googleSignInClient.signInIntent)
        }
        binding.tvRegister.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
        onBackPress()
    }

    private fun loginAccount(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            startActivity(Intent(context, MainActivity::class.java))
            requireActivity().finish()
            Toast.makeText(context, auth.currentUser?.email, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private val signInGoogle =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            if (task.result != null) {
                val credential = GoogleAuthProvider.getCredential(task.result.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    loginAccount(it)
                }
            } else {
                Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getGso(webClient: String): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClient)
            .requestEmail()
            .build()
    }

    private fun initList(view: View) {
        navController = Navigation.findNavController(view)
        val gso = getGso(getString(R.string.default_web_client_id))
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun loginAccount() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        if (validateForm(email, password)) {
            viewModel.loginEmailPassword(email, password)
        }
    }

    private fun validateForm(
        email: String,
        password: String,
    ): Boolean {
        var isFormValid = true
        if (email.isEmpty()) {
            isFormValid = false
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.error_text_empty_email)
        } else {
            binding.tilEmail.isErrorEnabled = false
        }
        if (password.length < 6) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error = getString(R.string.error_text_length_password)
        } else {
            binding.tilPassword.isErrorEnabled = false
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

    private fun onBackPress() {
        val onBackPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
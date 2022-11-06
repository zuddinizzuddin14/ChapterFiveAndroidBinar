package com.example.mymovieapp.presentation.ui.authuser

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentSplashBinding
import com.example.mymovieapp.presentation.ui.listmovie.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var navController: NavController

    private var _binding: FragmentSplashBinding? = null

    private val binding by lazy { _binding!! }

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(view)
        observeData()
    }

    private fun initList(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun observeData() {
        val user = viewModel.currentUser
        Handler(Looper.getMainLooper()).postDelayed({
            if (user != null) {
                Toast.makeText(context, "${user.email}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, MainActivity::class.java))
                requireActivity().finish()
            } else {
                navController.navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
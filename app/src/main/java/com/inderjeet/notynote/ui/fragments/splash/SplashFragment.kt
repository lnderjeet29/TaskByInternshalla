package com.inderjeet.notynote.ui.fragments.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.inderjeet.notynote.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private val binding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        navigateNext()
        return binding.root
    }

    private fun navigateNext() {
        lifecycleScope.launch {
            delay(2000) // Retain this if necessary

            val action = if (FirebaseAuth.getInstance().currentUser != null) {
                SplashFragmentDirections.actionSplashFragmentToNotesFragment()
            } else {
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            }

            findNavController().navigate(action)
        }
    }
}
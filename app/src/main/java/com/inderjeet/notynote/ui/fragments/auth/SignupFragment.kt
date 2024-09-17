package com.inderjeet.notynote.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.inderjeet.notynote.R
import com.inderjeet.notynote.databinding.FragmentLoginBinding
import com.inderjeet.notynote.databinding.FragmentSignupBinding
import com.inderjeet.notynote.utils.showToast


class SignupFragment : Fragment() {

    private val binding by lazy {
        FragmentSignupBinding.inflate(layoutInflater)
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View  {
        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirm.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToNotesFragment())
                            } else {
                                showToast(it.exception.toString())
                            }
                        }
                } else {
                    showToast("Password does not matched")
                }
            } else {
                showToast("Fields cannot be empty")
            }
        }

        binding.loginRedirectText.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}
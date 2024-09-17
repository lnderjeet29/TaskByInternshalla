package com.inderjeet.notynote.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.inderjeet.notynote.databinding.FragmentForgetPasswordBinding
import com.inderjeet.notynote.utils.showToast

class ForgetPasswordFragment : Fragment() {
    private val binding by lazy {
        FragmentForgetPasswordBinding.inflate(layoutInflater)
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding.resetPassword.setOnClickListener {
            val email = binding.resetEmail.text.toString()
            if (email.isNotEmpty()) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast("Check your email to reset your password!")
                    } else {
                        showToast(it.exception.toString())
                    }
                }
            } else {
                showToast("Enter Email")
            }
        }
        return binding.root
    }
}
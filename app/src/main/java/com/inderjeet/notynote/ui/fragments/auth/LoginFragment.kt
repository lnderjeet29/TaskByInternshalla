package com.inderjeet.notynote.ui.fragments.auth

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.inderjeet.notynote.R
import com.inderjeet.notynote.databinding.FragmentLoginBinding
import com.inderjeet.notynote.utils.showToast

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Google Sign-In options
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            loginButton.setOnClickListener { handleLogin() }
            forgotPassword.setOnClickListener { navigateToForgetPassword() }
            signupRedirectText.setOnClickListener { navigateToSignup() }
            btnGoogleSignIn.setOnClickListener {
                googleSignInActivityResultLauncher.launch(googleSignInClient.signInIntent)
            }
        }
    }

    private fun handleLogin() {
        val email = binding.loginEmail.text.toString().trim()
        val password = binding.loginPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            signInUser(email, password)
        } else {
            showToast("Fields cannot be empty")
        }
    }

    private fun signInUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateToNotes()
            } else {
                task.exception?.let { showToast(it.message ?: "Login failed") }
            }
        }
    }

    private val googleSignInActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                handleGoogleSignInResult(result.data)
            } else {
                Log.d(this::class.simpleName, "Google sign-in failed: ${result.data}")
            }
        }

    private fun handleGoogleSignInResult(data: Intent?) {
        val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = accountTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogleAccount(account)
        } catch (e: ApiException) {
            Log.d(this::class.simpleName, "Google sign-in failed: ${e.message}")
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                navigateToNotes()
            }
            .addOnFailureListener { error ->
                Log.d(this::class.simpleName, "Google sign-in error: ${error.message}")
                showToast(error.message ?: "Google sign-in failed")
            }
    }

    private fun navigateToNotes() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNotesFragment())
    }

    private fun navigateToForgetPassword() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment())
    }

    private fun navigateToSignup() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

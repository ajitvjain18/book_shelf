package com.techmania.bookshelf.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.techmania.bookshelf.viewmodel.LoginViewModel
import com.techmania.bookshelf.databinding.LayoutSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: LayoutSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        auth = Firebase.auth
        initClicks()
        initObservers()
//        setUpTextWatchers()
    }

    private fun initObservers() {
        with(viewModel) {
            countryListLiveData.observe(viewLifecycleOwner) { countryList ->
                val country = countryList.map { it.country }
                Log.d("ajit", "initObservers: " + country.map { it })
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                    country
                )
                binding.etCountry.setAdapter(arrayAdapter)
            }
        }
    }

    private fun setUpTextWatchers() {
        with(binding) {

        }
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            activity?.supportFragmentManager?.popBackStack()
//
//        }
//    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()]).{8,}$"
        val passwordMatcher = Regex(passwordPattern)
        return passwordMatcher.matches(password)
    }

    private fun initClicks() {
        with(binding) {
            signUpButton.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val country = etCountry.text.toString()

                if (email.isEmpty()) {
                    Toast.makeText(requireContext(), "Enter email address", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (password.isEmpty()) {
                    Toast.makeText(requireContext(), "Enter password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password.isNotEmpty() and !isValidPassword(password)) {
                    tvConditionsError.isVisible = true
                    tvConditionsError.text = "Weak password"
                    return@setOnClickListener
                } else if (email.isNotEmpty() and !isValidEmail(email)) {
                    tvConditionsError.isVisible = true
                    tvConditionsError.text = "Invalid email address"
                    return@setOnClickListener
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "User registered succesfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            val user = auth.currentUser
                            val intent = Intent(activity, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                requireContext(), task.exception.toString(), Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

            }

            etCountry.setOnClickListener {
                Log.d("ajit", "setOnClickListener: ")

                viewModel.fetchCountry()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val emailMatcher = Regex(emailPattern)
        return emailMatcher.matches(email)
    }

}
package com.techmania.bookshelf.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.techmania.bookshelf.R
import com.techmania.bookshelf.databinding.LayoutMainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LayoutMainActivityBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.layout_main_activity)
        auth = Firebase.auth
        initClicks()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initClicks() {
        with(binding) {
            tvNewUser.setOnClickListener {
                launchSignUpFragment()
            }
            binding.verify.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (email.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Enter email address", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (password.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Enter password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Login failed: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    private fun launchSignUpFragment() {
        val fragment = SignUpFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_from_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        transaction.add(binding.framelayout.id, fragment)
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commit()
    }
}
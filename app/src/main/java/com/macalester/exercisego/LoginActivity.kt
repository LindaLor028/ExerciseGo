package com.macalester.exercisego

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.macalester.exercisego.databinding.ActivityLoginBinding

/**
 * Code written by Péter Ekler during his Firebase Authentication Demo for his
 * Mobile Software Development course of Fall 2022.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener{
            registerUser()
        }
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    fun registerUser() {
        if (isFormValid()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            ).addOnSuccessListener {
                Toast.makeText(
                    this,
                    getString(R.string.registration_confirm),
                    Toast.LENGTH_LONG
                ).show()
            }.addOnFailureListener{
                Toast.makeText(
                    this,
                    "Error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    fun loginUser() {
        if (isFormValid()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            ).addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Login OK",
                    Toast.LENGTH_LONG
                ).show()

                startActivity(Intent(this, MapsActivity::class.java))
            }.addOnFailureListener{
                Toast.makeText(
                    this,
                    "Error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun isFormValid(): Boolean {
        return when {
            binding.etEmail.text.isEmpty() -> {
                binding.etEmail.error = getString(R.string.cannot_be_empty)
                false
            }
            binding.etPassword.text.isEmpty() -> {
                binding.etPassword.error = getString(R.string.cannot_be_empty)
                false
            }
            else -> true
        }
    }
}
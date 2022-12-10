package com.macalester.exercisego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.macalester.exercisego.adapter.ReviewAdapter
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.data.Review
import com.macalester.exercisego.databinding.ActivityReviewBinding
import java.util.*

class ReviewActivity : AppCompatActivity() {

    lateinit var binding : ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var parkKey = intent.getStringExtra("parkID")!!

        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            if (!binding.etReviewInput.text.isNullOrEmpty()) {
                val userReview = Review(FirebaseAuth.getInstance().currentUser!!.email!!, parkKey, binding.rating.rating, binding.etReviewInput.text.toString())
                uploadReview(userReview)
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun uploadReview(review : Review) {
        val reviewCollection = FirebaseFirestore.getInstance()
            .collection("reviews") // TODO: Do not hard code "reviews"; turn it into a companion obj thing later ! PLEASE

        reviewCollection.add(review)
            .addOnSuccessListener {
                Toast.makeText(this,
                    "Review saved", Toast.LENGTH_SHORT).show()

                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this,
                    "Error: ${it.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}
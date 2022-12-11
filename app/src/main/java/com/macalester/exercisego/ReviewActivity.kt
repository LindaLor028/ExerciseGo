package com.macalester.exercisego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.macalester.exercisego.data.Review
import com.macalester.exercisego.databinding.ActivityReviewBinding

/**
 * Code written by Linda Lor (LindaLor028 on GitHub).
 */
class ReviewActivity : AppCompatActivity() {

    lateinit var binding : ActivityReviewBinding

    /**
     * Creates ReviewActivity and updates UI as needed and
     * reads button-clicks.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var parkKey = intent.getStringExtra(MapsActivity.PARK_ID)!!

        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            if (!binding.etReviewInput.text.isNullOrEmpty()) {
                val userReview = Review(FirebaseAuth.getInstance().currentUser!!.email!!, parkKey, binding.rating.rating, binding.etReviewInput.text.toString())
                uploadReview(userReview)
                finish()
            }
        }
    }

    /**
     * Adds a back button to the Action Bar and finishes the activity when clicked.
     * Code provided by user, arjun shrestha, on stackoverflow.
     * https://stackoverflow.com/questions/47250263/kotlin-convert-timestamp-to-datetime
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Uploads user review into Firebase Cloud and informs user via Toast Messages
     * if the upload is complete.
     */
    private fun uploadReview(review : Review) {
        val reviewCollection = FirebaseFirestore.getInstance()
            .collection(MapsActivity.REVIEWS_COLLECTION)

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
package com.macalester.exercisego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
        // TODO: Do I even need this? :) Looks like I just need the parkID information !
//        var firebasePark = FirebaseFirestore.getInstance().collection("parks").document(parkKey)
//        firebasePark.get().addOnSuccessListener {
//        }

        // use parkID to give it to reviews key

        setContentView(binding.root)

       //binding.tvReviewTitle.text = park.name


        // TODO: Verify if we even need this (not sure if we do LOL)
        binding.rating.setOnRatingBarChangeListener { ratingBar, fl, b ->  }

        binding.btnSubmit.setOnClickListener {

            if (!binding.etReviewInput.text.isNullOrEmpty()) {
                // create a Review Object
                val userReview = Review("random_user_id", "author", binding.rating.rating, binding.etReviewInput.text.toString())
                // add it to the park object's list of reviews

                // then update the firebase (?) :) :) Nervous laughter ..
//            finish()
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
}
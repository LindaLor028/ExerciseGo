package com.macalester.exercisego

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.macalester.exercisego.adapter.ReviewAdapter
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    lateinit var adapter : ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Park Details")

        if (intent.hasExtra("parkID") &&
            !intent.getStringExtra("parkID").isNullOrEmpty()) {

            var parkKey = intent.getStringExtra("parkID")!!
            var firebasePark = FirebaseFirestore.getInstance().collection("parks").document(parkKey)
            firebasePark.get().addOnSuccessListener {
                val park = it.toObject(Park::class.java)

                binding.tvParkName.text = park!!.name
                binding.tvAddress.text = park!!.address

                setFavoriteLogo(park!!, parkKey)

                binding.ibFavorite.setOnClickListener {
                    park.isFavorite = !park.isFavorite
                    setFavoriteLogo(park, parkKey)
                }

                binding.btnReview.setOnClickListener {
                    // you can probably implement the firebase/ait-forum demo thing :))
                    startReviewActivity(park)
                }
            }

            adapter = ReviewAdapter(this)
            binding.rvReviews.adapter = adapter
            setContentView(binding.root)
        }
    }

    private fun startReviewActivity (park : Park) {
        val intentDetails = Intent()
        intentDetails.setClass(this, ReviewActivity::class.java)
        intentDetails.putExtra("parkID", "TxMjfJC01GYeblLqNM1S") // TODO: Fix This !
        ContextCompat.startActivity(this, intentDetails, null)
    }

    // TODO: Figure out how to update park favorite status, we when we go back; it is now favorited !
    private fun setFavoriteLogo(park : Park, parkKey : String) {
        if (park.isFavorite) {
            // set star logo to turned on
            binding.ibFavorite.setImageResource(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_light_normal)
        }
        else {
            // set star logo to turned off
            binding.ibFavorite.setImageResource(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_disabled)
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
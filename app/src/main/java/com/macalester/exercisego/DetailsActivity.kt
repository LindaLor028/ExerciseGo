package com.macalester.exercisego

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.firestore.*
import com.macalester.exercisego.adapter.ReviewAdapter
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.data.Review
import com.macalester.exercisego.databinding.ActivityDetailsBinding

/**
 * Code written by Linda Lor (LindaLor028 on GitHub).
 */
class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    lateinit var adapter : ReviewAdapter
    private var snapshotListener : ListenerRegistration? = null
    private var parkID = ""

    /**
     * Creates DetailsActivity and updates UI as needed.
     * Also reads when buttons are clicked and sends appropriate response.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        parkID = intent.getStringExtra(MapsActivity.PARK_ID)!!
        var firebasePark = FirebaseFirestore.getInstance().collection(MapsActivity.PARKS_COLLECTION).document(parkID)
        firebasePark.get().addOnSuccessListener {
            val park = it.toObject(Park::class.java)
            adapter = ReviewAdapter(this)
            binding.rvReviews.adapter = adapter
            setContentView(binding.root)

            queryReviews()
            setUpParkDetails(park!!)

            binding.btnReview.setOnClickListener {
                startReviewActivity()
            }
        }
    }

    /**
     * Overall method that sets up all park details.
     */
    private fun setUpParkDetails(park : Park) {
        binding.tvParkName.text = park!!.name
        binding.tvAddress.text = park!!.address

        setUpEquipmentRow(park)
        setImages(park)
    }

    /**
     * Goes through park exercise equipment attributes and sets the visibility of
     * each equipment as needed.
     */
    private fun setUpEquipmentRow(park: Park) {
        if (park.hasBar) {
            binding.equipmentsRow.imgBars.visibility = View.VISIBLE
            binding.equipmentsRow.tvBars.visibility = View.VISIBLE
        }
        if (park.hasBench) {
            binding.equipmentsRow.imgBench.visibility = View.VISIBLE
            binding.equipmentsRow.tvBenches.visibility = View.VISIBLE
        }
        if (park.hasCycle) {
            binding.equipmentsRow.imgCycle.visibility = View.VISIBLE
            binding.equipmentsRow.tvCycles.visibility = View.VISIBLE
        }
        if (park.hasPress) {
            binding.equipmentsRow.imgPress.visibility = View.VISIBLE
            binding.equipmentsRow.tvPresses.visibility = View.VISIBLE
        }
        if (park.hasTwister) {
            binding.equipmentsRow.imgTwist.visibility = View.VISIBLE
            binding.equipmentsRow.tvTwisters.visibility = View.VISIBLE
        }
        if (park.hasPress) {
            binding.equipmentsRow.imgPress.visibility = View.VISIBLE
            binding.equipmentsRow.tvPresses.visibility = View.VISIBLE
        }
        if(park.hasWalker) {
            binding.equipmentsRow.imgWalker.visibility = View.VISIBLE
            binding.equipmentsRow.tvWalkers.visibility = View.VISIBLE
        }
    }

    /**
     * Sets up all four images of park based on its image URL.
     */
    private fun setImages(park : Park) {
        setParkImage(binding.imageRow.iv1, park.imgURL_1)
        setParkImage(binding.imageRow.iv2, park.imgURL_2)
        setParkImage(binding.imageRow.iv3, park.imgURL_3)
        setParkImage(binding.imageRow.iv4, park.imgURL_4)
    }

    /**
     * Sets up the one image of regarding the park.
     */
    private fun setParkImage(img : ImageView, url : String) {
        if (url != "") {
            img.visibility = View.VISIBLE
            Glide.with(this).load(url).into (
                img
            )
        }
        else {
            img.visibility = View.GONE
        }
    }

    /**
     * Starts ReviewActivity and sends appropriate information concerning
     * the parkID through intentDetails.
     */
    private fun startReviewActivity () {
        val intentDetails = Intent()
        intentDetails.setClass(this, ReviewActivity::class.java)
        intentDetails.putExtra(MapsActivity.PARK_ID, parkID)
        ContextCompat.startActivity(this, intentDetails, null)
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
     * Queries the reviews from the Firebase Cloud.
     */
    fun queryReviews() {
        val queryReviews = FirebaseFirestore.getInstance().collection(MapsActivity.REVIEWS_COLLECTION)

        val eventListener = object : EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?,
                                 e: FirebaseFirestoreException?) {
                if (e != null) {
                    Toast.makeText(
                        this@DetailsActivity, "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                for (docChange in querySnapshot?.getDocumentChanges()!!) {
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val review = docChange.document.toObject(Review::class.java)
                        if (review.parkid == parkID) {
                            adapter.addReview(review, docChange.document.id)
                        }
                    } else if (docChange.type == DocumentChange.Type.REMOVED) {
                        adapter.removeReviewByKey(docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {
                    }
                }
            }
        }
        snapshotListener = queryReviews.addSnapshotListener(eventListener)
    }
}
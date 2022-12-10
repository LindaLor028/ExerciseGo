package com.macalester.exercisego

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.*
import com.macalester.exercisego.adapter.ReviewAdapter
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.data.Review
import com.macalester.exercisego.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    lateinit var adapter : ReviewAdapter
    private var snapshotListener : ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Park Details")

        var parkKey = intent.getStringExtra("parkID")!!
        var firebasePark = FirebaseFirestore.getInstance().collection("parks").document(parkKey)
        firebasePark.get().addOnSuccessListener {
            val park = it.toObject(Park::class.java)

            binding.tvParkName.text = park!!.name
            binding.tvAddress.text = park!!.address

            binding.btnReview.setOnClickListener {
                // you can probably implement the firebase/ait-forum demo thing :))
                startReviewActivity(parkKey) }

            queryReviews(parkKey)
            setUpEquipmentRow(park)
            adapter = ReviewAdapter(this)
            binding.rvReviews.adapter = adapter
            setContentView(binding.root)
        }
    }

    //TODO: Check if we can make this less redundant?
    private fun setUpEquipmentRow(park: Park) {
        // go through its park attributes and set visibility as needed
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

    private fun startReviewActivity (parkID : String) {
        val intentDetails = Intent()
        intentDetails.setClass(this, ReviewActivity::class.java)
        intentDetails.putExtra("parkID", parkID) // TODO: Fix This !
        ContextCompat.startActivity(this, intentDetails, null)
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

    fun queryReviews(parkKey : String) {
        // point to collections table
        //TODO: Get reviews specific to parkID
        val queryReviews = FirebaseFirestore.getInstance().collection("reviews")

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
                        if (review.parkid == parkKey) {
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
package com.macalester.exercisego.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.macalester.exercisego.DetailsActivity
import com.macalester.exercisego.MapsActivity
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.data.Review
import com.macalester.exercisego.databinding.ParkRowBinding

class NearbyAdapter : RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    // store list for post Objs
    var  parksList = mutableListOf<Park>()
    // store list for Post IDS (this is bc Firebase store post ids a lil diff)
    var  parkKeys = mutableListOf<String>()

    val context: Context


    constructor(context: Context) {
        this.context = context
    }

    override fun getItemCount(): Int {
        return parksList.size
    }

    private fun startDetailsActivity (index : Int) {
        val intentDetails = Intent()
        intentDetails.setClass(context, DetailsActivity::class.java)

        val parkKey = parkKeys[index]
        intentDetails.putExtra("parkID", parkKey)
        ContextCompat.startActivity(context, intentDetails, null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val parkRowBinding = ParkRowBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(parkRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPark = parksList[holder.adapterPosition]
        holder.bind(currentPark)
    }

    fun addPark(park: Park, key: String) {
        parksList.add(park)
        parkKeys.add(key)
        notifyItemInserted(parksList.lastIndex)
    }

    private fun removePark(index: Int) {
        FirebaseFirestore.getInstance().collection(
            "parks").document(
            parkKeys[index]
        ).delete()

        parksList.removeAt(index)
        parkKeys.removeAt(index)
        notifyItemRemoved(index)
    }

    // when somebody else removes an object
    fun removeParkByKey(key: String) {
        val index = parkKeys.indexOf(key)
        if (index != -1) {
            parksList.removeAt(index)
            parkKeys.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    // represents one line and is copied for every other item !
    inner class ViewHolder(private val parkRowBinding: ParkRowBinding) :
        RecyclerView.ViewHolder(parkRowBinding.root)
    {
        fun bind(park : Park) {

            parkRowBinding.tvRowName.text = park.name
            parkRowBinding.tvRowRatings.text = park.overallRatings.toString()

            parkRowBinding.background.setOnClickListener {
                // You need to get a marker on the map with the park name :)
            }

            parkRowBinding.btnMore.setOnClickListener {
                val index = parksList.indexOf(park)
                startDetailsActivity(index)
            }

        }
    }
}
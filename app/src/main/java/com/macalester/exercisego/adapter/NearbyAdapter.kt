package com.macalester.exercisego.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.macalester.exercisego.DetailsActivity
import com.macalester.exercisego.MapsActivity
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.databinding.ParkRowBinding

/**
 * Code adapted from Péter Ekler 's To do Recycler Demo.
 */
class NearbyAdapter : RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    var  parksList = mutableListOf<Park>()
    var  parkIDs = mutableListOf<String>()
    val context: Context

    /**
     * ReviewAdapter constructor which takes in context.
     */
    constructor(context: Context) {
        this.context = context
    }

    /**
     * Method for starting the DetailsActivity.
     * It ensures that the proper park ID is passed through intentDetails.
     */
    private fun startDetailsActivity (index : Int) {
        val intentDetails = Intent()
        intentDetails.setClass(context, DetailsActivity::class.java)

        val parkKey = parkIDs[index]
        intentDetails.putExtra(MapsActivity.PARK_ID, parkKey)
        ContextCompat.startActivity(context, intentDetails, null)
    }

    /**
     * Creates the inner ViewHolder class and binds it to the context.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val parkRowBinding = ParkRowBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(parkRowBinding)
    }

    /**
     * Method ensures proper binding on the inner ViewHolder class in respect to the
     * park details it hold.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPark = parksList[holder.adapterPosition]
        holder.bind(currentPark)
    }

    /**
     * Returns overall amount of parks in Firebase Cloud
     */
    override fun getItemCount(): Int {
        return parksList.size
    }

    /**
     * Adds the park by key and updates
     * parksList and parkIDs accordingly.
     */
    fun addPark(park: Park, key: String) {
        parksList.add(park)
        parkIDs.add(key)
        notifyItemInserted(parksList.lastIndex)
    }

    /**
     * Removes the park by key and updates
     * parksList and parkIDs accordingly.
     */
    fun removeParkByKey(key: String) {
        val index = parkIDs.indexOf(key)
        if (index != -1) {
            parksList.removeAt(index)
            parkIDs.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    /**
     * An inner class that represents a singular row for a park.
     * It manages binding and updating the UI based on the park details.
     */
    inner class ViewHolder(private val parkRowBinding: ParkRowBinding) :
        RecyclerView.ViewHolder(parkRowBinding.root)
    {
        fun bind(park : Park) {

            parkRowBinding.tvRowName.text = park.name

            var distance = park.distance.toString()
            if (distance.length > 3) {
                distance = distance.substring(0..1)
            }
            parkRowBinding.tvRowDistance.text = "${distance} m"

            parkRowBinding.background.setOnClickListener {
                val index = parksList.indexOf(park)
                startDetailsActivity(index)
            }


        }
    }
}
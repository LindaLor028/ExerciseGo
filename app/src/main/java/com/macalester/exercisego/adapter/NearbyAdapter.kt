package com.macalester.exercisego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.databinding.ParkRowBinding

class NearbyAdapter : RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    private var nearbyParks = mutableListOf<Park>(
        Park("Happy Exercise Park", 4.5f, false)
    )
    val context: Context

    constructor(context: Context) {
        this.context = context
    }

    override fun getItemCount(): Int {
        return nearbyParks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val parkRowBinding = ParkRowBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(parkRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPark = nearbyParks[holder.adapterPosition]
        holder.bind(currentPark)
    }

    // represents one line and is copied for every other item !
    inner class ViewHolder(private val parkRowBinding: ParkRowBinding) :
        RecyclerView.ViewHolder(parkRowBinding.root)
    {
        fun bind(park : Park) {

            parkRowBinding.tvRowName.text = park.parkName
            parkRowBinding.tvRowRatings.text = park.overallRatings.toString()

            parkRowBinding.background.setOnClickListener {
                // when clicked open the screen to a new park !
            }
        }
    }
}
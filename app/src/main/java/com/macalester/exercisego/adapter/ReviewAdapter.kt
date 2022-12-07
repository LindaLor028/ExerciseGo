package com.macalester.exercisego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macalester.exercisego.data.Review
import com.macalester.exercisego.databinding.ReviewRowBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    val context: Context
    private var reviews = mutableListOf<Review>()

    constructor(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val reviewRowBinding = ReviewRowBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(reviewRowBinding)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val currentReview = reviews[holder.adapterPosition]
        holder.bind(currentReview)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    // represents one line and is copied for every other item !
    inner class ViewHolder(private val reviewRowBinding: ReviewRowBinding) :
        RecyclerView.ViewHolder(reviewRowBinding.root)
    {
        fun bind(review : Review) {

            reviewRowBinding.tvUsername.text = review.author
            reviewRowBinding.tvReviewPreview.text = review.body // TODO: Only get XX number of chars to show in the preview
            reviewRowBinding.tvRatings.text = review.ratings.toString()

            // TODO: Implement a dialogue that users can click on to view the whole review

        }
    }
}
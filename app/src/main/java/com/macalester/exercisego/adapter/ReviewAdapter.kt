package com.macalester.exercisego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macalester.exercisego.data.Review
import com.macalester.exercisego.databinding.ReviewRowBinding

/**
 * Code adapted from Péter Ekler 's To do Recycler Demo.
 */
class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    var  reviewsList = mutableListOf<Review>()
    var  reviewIDs = mutableListOf<String>()
    val context: Context

    /**
     * ReviewAdapter constructor which takes in context.
     */
    constructor(context: Context) {
        this.context = context
    }

    /**
     * Creates the inner ViewHolder class and binds it to the context.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val reviewRowBinding = ReviewRowBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(reviewRowBinding)
    }

    /**
     * Method ensures proper binding on the inner ViewHolder class in respect to the
     * review details it hold.
     */
    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val currentReview = reviewsList[holder.adapterPosition]
        holder.bind(currentReview)
    }

    /**
     * Returns overall amount of reviews in Firebase Cloud
     */
    override fun getItemCount(): Int {
        return reviewsList.size
    }

    /**
     * Adds the review by key and updates
     * reviewsList and reviewIDs accordingly.
     */
    fun addReview(review: Review, key: String) {
        reviewsList.add(review)
        reviewIDs.add(key)
        notifyItemInserted(reviewsList.lastIndex)
    }

    /**
     * Removes the review by key and updates
     * reviewsList and reviewIDs accordingly.
     */
    fun removeReviewByKey(key: String) {
        val index = reviewIDs.indexOf(key)
        if (index != -1) {
            reviewsList.removeAt(index)
            reviewIDs.removeAt(index)
            notifyItemRemoved(index)
        }
    }

     /**
     * An inner class that represents a singular row for a review.
     * It manages binding and updating the UI based on the review details.
     */
    inner class ViewHolder(private val reviewRowBinding: ReviewRowBinding) :
        RecyclerView.ViewHolder(reviewRowBinding.root)
    {
        fun bind(review : Review) {
            reviewRowBinding.tvUsername.text = review.uid
            reviewRowBinding.tvReviewPreview.text = review.body
            reviewRowBinding.tvRatings.text = review.ratings.toString()
        }
    }
}
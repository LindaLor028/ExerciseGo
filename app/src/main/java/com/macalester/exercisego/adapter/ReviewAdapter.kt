package com.macalester.exercisego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.data.Review
import com.macalester.exercisego.databinding.ReviewRowBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ViewHolder> {


    // store list for post Objs
    var  reviewsList = mutableListOf<Review>()
    // store list for Post IDS (this is bc Firebase store post ids a lil diff)
    var  reviewKeys = mutableListOf<String>()

    val context: Context

    constructor(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val reviewRowBinding = ReviewRowBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(reviewRowBinding)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val currentReview = reviewsList[holder.adapterPosition]
        holder.bind(currentReview)
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    fun addReview(review: Review, key: String) {
        reviewsList.add(review)
        reviewKeys.add(key)
        notifyItemInserted(reviewsList.lastIndex)
    }

    private fun removeReview(index: Int) {
        FirebaseFirestore.getInstance().collection(
            "parks").document(
            reviewKeys[index]
        ).delete()

        reviewsList.removeAt(index)
        reviewKeys.removeAt(index)
        notifyItemRemoved(index)
    }

    // when somebody else removes an object
    fun removeReviewByKey(key: String) {
        val index = reviewKeys.indexOf(key)
        if (index != -1) {
            reviewsList.removeAt(index)
            reviewKeys.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    // represents one line and is copied for every other item !
    inner class ViewHolder(private val reviewRowBinding: ReviewRowBinding) :
        RecyclerView.ViewHolder(reviewRowBinding.root)
    {
        fun bind(review : Review) {
            reviewRowBinding.tvUsername.text = review.uid
            reviewRowBinding.tvReviewPreview.text = review.body
            reviewRowBinding.tvRatings.text = review.ratings.toString()

            // TODO: Implement a dialogue that users can click on to view the whole review

        }
    }
}
package com.macalester.exercisego.data

/**
 * Review data class.
 * Contains appropriate information that defines a review in this app.
 */
data class Review(
    var uid: String = "",
    var parkid : String = "",
    var ratings: Float = 0f,
    var body: String = ""
)
package com.macalester.exercisego.data

data class Review(
    var uid: String = "",
    var author: String = "",
    var ratings: Float,
    var body: String = ""
)
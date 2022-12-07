package com.macalester.exercisego.data

data class Review(
    var uid: String = "",
    var parkid : String = "",
    var ratings: Float = 0f,
    var body: String = ""
)
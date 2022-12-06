package com.macalester.exercisego.data

data class Park(
    var parkName : String,
    var overallRatings : Float,
    // you need park coordinates, park reviews (you might need review data type)
    var isFavorite : Boolean) : java.io.Serializable {
}
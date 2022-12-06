package com.macalester.exercisego.data

import com.google.android.gms.maps.model.LatLng

data class Park(
    var name : String,
    var overallRatings : Float,
    var address : String,
//    var location : LatLng,
    // you need park coordinates, park reviews (you might need review data type)
    var isFavorite : Boolean) : java.io.Serializable {
}
package com.macalester.exercisego.data

/**
 * Park data class.
 * Contains appropriate information that defines a park in this app.
 */
data class Park(
    var name : String = "",
    var address : String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var distance : Double = 0.0,

    // exercise equipment details
    var hasCycle : Boolean = false,
    var hasTwister : Boolean = false,
    var hasPress : Boolean = false,
    var hasWalker : Boolean = false,
    var hasBar : Boolean = false,
    var hasBench : Boolean = false,

    // image URLS
    var imgURL_1 : String = "",
    var imgURL_2 : String = "",
    var imgURL_3 : String = "",
    var imgURL_4 : String = ""


)

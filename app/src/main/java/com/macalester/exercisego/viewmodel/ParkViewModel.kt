package com.macalester.exercisego.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.macalester.exercisego.data.Park

class ParkViewModel (application: Application) :
    AndroidViewModel(application) {

    lateinit var allParks: MutableList<Park>

    fun insertPark(park : Park) {
        allParks[0] = park // this list will only contain ONE park; and that park is the sole park
    }


}
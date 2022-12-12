package com.macalester.exercisego

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.*
import com.macalester.exercisego.adapter.NearbyAdapter
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.databinding.ActivityMapsBinding

/**
 * Code written by Linda Lor (LindaLor028 on GitHub).
 */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    companion object {
        const val PARKS_COLLECTION = "parks"
        const val REVIEWS_COLLECTION = "reviews"
        const val PARK_ID = "parkID"
        const val URL_KEY = "imgURL"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var adapter: NearbyAdapter
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager: LocationManager

    private var snapshotListener : ListenerRegistration? = null
    private var userLocation : LatLng = LatLng(0.0,0.0)
    private var pause = false
    private var locationPermission = false

    /**
     * Creates MapsActivity and updates UI as needed and
     * reads button-clicks.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NearbyAdapter(this)
        binding.rvNearbyParks.adapter = adapter

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = getSystemService(android.content.Context.LOCATION_SERVICE) as android.location.LocationManager
        shareUserLocation()
        addParkMarkers()

        queryParks()
    }

    /**
     * Resumes the location-reading on resume.
     */
    override fun onResume() {
        super.onResume()
        pause = false
    }

    /**
     * Pauses the location-reading on stop.
     */
    override fun onStop() {
        super.onStop()
        pause = true
    }

    /**
     * Sets up Map with appropriate markers.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val budapest = LatLng(47.49, 19.04)
        mMap.addMarker(MarkerOptions().position(budapest).title("Marker in Budapest"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(budapest))

        val cameraPosition = CameraPosition.Builder()
            .target(budapest)
            .zoom(17f )
            .bearing(-45f )
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }

    /**
     * Requests location permissions and then calls a requestLocationUpdate for the locationManager.
     * Based on code from JavaPapers: https://javapapers.com/android/get-current-location-in-android/
     */
    private fun shareUserLocation() {
        if (!pause)  {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            } else {
                locationPermission = true
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            }
        }
    }

    /**
     * Queries the parks from the Firebase Cloud and presents them in order of distance.
     */
    fun queryParks() {
        val queryPosts = FirebaseFirestore.getInstance().collection(PARKS_COLLECTION).orderBy("distance")

        val eventListener = object : EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?,
                                 e: FirebaseFirestoreException?) {
                if (e != null) {
                    Toast.makeText(
                        this@MapsActivity, "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                for (docChange in querySnapshot?.getDocumentChanges()!!) {
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val park = docChange.document.toObject(Park::class.java)
                        adapter.addPark(park, docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.REMOVED) {
                        adapter.removeParkByKey(docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {
                    }
                }
            }
        }
        snapshotListener = queryPosts.addSnapshotListener(eventListener)
    }

    /**
     * Add a single marker on map based on park object's latitude and longitude.
     */
    private fun addMarker(park : Park) {
        val parkCoords = LatLng(park.latitude, park.longitude)
        mMap.addMarker(MarkerOptions().position(parkCoords).title(park.name))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(parkCoords))

    }

    /**
     * Adds marker for first three parks.
     */
    private fun addParkMarkers() {
        FirebaseFirestore.getInstance().collection(PARKS_COLLECTION).get().addOnSuccessListener {
            val parks = it.documents
            for (park in parks) {
                val parkObj = park.toObject(Park::class.java)
                addMarker(parkObj!!)
            }
        }
    }

    /**
     * Calculates the distance from the userLocation to each park in the Firebase Cloud.
     * Then updates the distance field in Firebase Cloud.
     */
    private fun calculateParkDistance() {
        FirebaseFirestore.getInstance().collection(PARKS_COLLECTION).get().addOnSuccessListener {
            val parks = it.documents
            val postsCollection = FirebaseFirestore.getInstance().collection(PARKS_COLLECTION)
            for (park in parks) {
                val parkObj = park.toObject(Park::class.java)

                val parkLocation = Location("parkLocation")
                parkLocation.latitude = parkObj!!.latitude
                parkLocation.longitude = parkObj!!.longitude

                val startLocation = Location("userLocation")
                startLocation.latitude = userLocation.latitude
                startLocation.longitude = userLocation.longitude

                postsCollection.document(park.id).update("distance", startLocation.distanceTo(parkLocation)/1000)
            }
        }
    }

    /**
     * Updates userLocation if location changes.
     */
    override fun onLocationChanged(location: Location) {
        userLocation = LatLng(location.latitude, location.longitude)
        calculateParkDistance()
    }

    /**
     * When app is closed, remove snapshopListener to prevent background reading.
     */
    override fun onDestroy() {
        super.onDestroy()
        snapshotListener?.remove()
    }
}
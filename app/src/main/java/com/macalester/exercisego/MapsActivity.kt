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
        val PARKS_COLLECTION = "parks"
        val REVIEWS_COLLECTION = "reviews"
        val PARK_ID = "parkID"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var adapter: NearbyAdapter
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager: LocationManager

    private var snapshotListener : ListenerRegistration? = null
    private var userLocation : LatLng = LatLng(0.0,0.0)

    /**
     * Creates MapsActivity and updates UI as needed and
     * reads button-clicks.
     *
     * TODO: Override onResume and onStop in respect to location monitoring
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

        binding.btnTest.setOnClickListener {
            uploadPark()
            calculateParkDistance()
        }
        requestNeededPermission()

        queryParks()
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
     * Requests location permissions.
     */
    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        } else {
        }
    }


    /**
     * Requests location permissions and then calls a requestLocationUpdate for the locationManager.
     */
    private fun shareUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
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
     * TODO: Get first three parks, make sure this matches with top 3 !
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

                postsCollection.document(park.id).update("distance", startLocation.distanceTo(parkLocation))
            }
        }

    }

    /**
     * TODO: Delete Later!
     */
    private fun uploadPark() {
        val parkLat = 47.5057808
        val parkLong = 19.0669936

        val testPark =
            Park("Hunyadi Tér" , "Budapest, Hunyadi tér, 1067 Hungary" , parkLat, parkLong , 0.0, false, true, true, false, true)

        val postsCollection = FirebaseFirestore.getInstance()
            .collection(PARKS_COLLECTION)

        postsCollection.add(testPark)
            .addOnSuccessListener {
                Toast.makeText(this,
                    "Park saved", Toast.LENGTH_SHORT).show()

                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this,
                    "Error: ${it.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }

    /**
     * Updates userLocation if location changes.
     */
    override fun onLocationChanged(location: Location) {
        userLocation = LatLng(location.latitude, location.longitude)
    }
}
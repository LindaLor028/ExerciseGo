package com.macalester.exercisego

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.*
import com.macalester.exercisego.adapter.NearbyAdapter
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit var adapter: NearbyAdapter
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationRequest : com.google.android.gms.location.LocationRequest
    private lateinit var mFusedLocationClient : FusedLocationProviderClient
    private lateinit var locationManager: LocationManager

    private var snapshotListener : ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NearbyAdapter(this)
        binding.rvNearbyParks.adapter = adapter

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = getSystemService(android.content.Context.LOCATION_SERVICE) as android.location.LocationManager

       // mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        shareUserLocation()

        binding.btnTest.setOnClickListener {
            uploadPark()
            //shareUserLocation()
        }
        requestNeededPermission()

        queryParks()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val budapest = LatLng(47.49, 19.04)
        mMap.addMarker(MarkerOptions().position(budapest).title("Marker in Budapest"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(budapest))
    }

    fun requestNeededPermission() {
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
            // we have the permission
            //myLocationManager.startLocationMonitoring()
        }
    }


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


    // query db from firebase
    fun queryParks() {
        // point to collections table
        val queryPosts = FirebaseFirestore.getInstance().collection("parks")
        // won't be in consistent order unless you order the items!! Like .orderBy(stuff) ..
        // You can even create filters :)

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

    fun previewPark(parkKey : String) {
        // firebase call the park by index
        //  val parkKey = parkKeys[index] <-- we need the parkKey sent to us; not the index!
        var firebasePark = FirebaseFirestore.getInstance().collection("parks").document(parkKey)
        firebasePark.get().addOnSuccessListener {
            val park = it.toObject(Park::class.java)
            val parkLocation = LatLng(park!!.latitude, park!!.longitude)
            mMap.addMarker(MarkerOptions().position(parkLocation).title(park.name))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(parkLocation))
        }
        // add a marker on the map
            // figure out how to remove the marker once the user clicks "out"
        //

    }

    private fun uploadPark() {
        val parkLat = 47.5057808
        val parkLong = 19.0669936

        val testPark =
            Park("Hunyadi Tér", 4.5f, "Budapest, Hunyadi tér, 1067 Hungary" , parkLat, parkLong , false, true, true, false, true, false)

        val postsCollection = FirebaseFirestore.getInstance()
            .collection("parks")

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

    override fun onLocationChanged(location: Location) {
        binding.tvUserLocation.text = """
            ${location.latitude}, 
            ${location.longitude}            
        """.trimIndent()
    }


}
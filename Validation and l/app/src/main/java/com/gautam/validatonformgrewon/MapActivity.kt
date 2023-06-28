package com.gautam.validatonformgrewon

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.*
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gautam.validatonformgrewon.databinding.ActivityMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener,
    GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraIdleListener {

    var searchView: SearchView? = null
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var binding: ActivityMapBinding
    private var locationArrayList: ArrayList<LatLng>? = null
    lateinit var currentadress: TextView

    var requestPermissionAudio =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            Log.e("TAG", "m: ")
            if (permission.keys.contains(Manifest.permission.ACCESS_FINE_LOCATION) || permission.keys.contains(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                if (permission[Manifest.permission.ACCESS_FINE_LOCATION] == true && permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                    Log.e("TAG", "agdgdda: ")
                    if (isLocationEnabled()) {
                        Log.e("TAG", "ada: ")
                        getCordinate()
                    } else {
                        Log.e("TAG", "ggff: ")
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                } else {
                    Toast.makeText(this, getString(R.string.locationdenied), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationArrayList = ArrayList()
        searchView = binding.SearchView

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val location = searchView!!.query.toString()
                var addressList: List<Address>? = null
                val geocoder = Geocoder(this@MapActivity)
                try {
                    addressList = geocoder.getFromLocationName(location, 1)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val address: Address = addressList!![0]
                val latLng = LatLng(address.getLatitude(), address.getLongitude())
                mMap.addMarker(MarkerOptions().position(latLng).title(location))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        // mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        Log.e("TAG", "onMapReady: ")
        mMap = googleMap
        requestPermission()
        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnCameraIdleListener(this)

    }

    override fun onResume() {
        super.onResume()
        if (isLocationEnabled()) {
            getCordinate()
            Log.e("TAG", "onResume: " + getCordinate())
            Toast.makeText(this, "  Your Gps  on", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "  Your Gps  off", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCordinate() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.let {
                val lat = it.latitude
                val long = it.longitude
                val geocoder = Geocoder(applicationContext)
                val addresses = geocoder.getFromLocation(lat, long, 5)
                val myAddress: String = addresses?.get(0)?.getAddressLine(0)
                    .toString() + "," + (addresses?.get(0)?.countryName)
                val coordinates = LatLng(lat, long)
                mMap.addMarker(
                    MarkerOptions().position(coordinates).title(myAddress).icon(
                        BitmapFromVector(
                            getApplicationContext(),
                            R.drawable.ic_baseline_flag_24
                        )
                    )
                )

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15.0f))
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }

    private fun requestPermission() {
        Log.e("TAG", "r: ")
        requestPermissionAudio.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

    }

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas. 
        val canvas = Canvas(bitmap)

        // below line is use to draw our 
        // vector drawable in canvas. 
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap. 
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(location!!.latitude, location.longitude, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        setAddress(addresses!![0])
    }

    private fun setAddress(address: Address) {
        if (address != null) {
            if (address.getAddressLine(0) != null) {
                binding.SearchView.setQuery(address.getAddressLine(0), false)
            }
            if (address.getAddressLine(1) != null) {
                binding.SearchView.setQuery(
                    currentadress.getText().toString() + address.getAddressLine(1), false
                )
            }
        }

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)

    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }

    override fun onCameraMoveStarted(p0: Int) {

    }

    override fun onCameraIdle() {
        var adress: List<Address>? = null
        val geocoder = Geocoder(this, Locale.getDefault())
        try {

            adress = geocoder.getFromLocation(
                mMap.cameraPosition.target.latitude,
                mMap.cameraPosition.target.longitude, 1
            )
            setAddress(adress!![0])
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

}









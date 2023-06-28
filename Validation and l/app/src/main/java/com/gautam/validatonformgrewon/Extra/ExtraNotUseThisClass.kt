package com.gautam.validatonformgrewon.Extra

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.gautam.validatonformgrewon.databinding.ActivityMessageBinding
import com.gautam.validatonformgrewon.modal.SendDataFaireBase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ExtraNotUseThisClass {

    /*class MessageActivity : AppCompatActivity() {

        lateinit var biniding: ActivityMessageBinding
        var firebaseDatabase: FirebaseDatabase? = null//
        var databaseReference: DatabaseReference? = null//dbrefrence


        @SuppressLint("SuspiciousIndentation")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            biniding = ActivityMessageBinding.inflate(layoutInflater)
            setContentView(biniding.root)

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase!!.getReference("User")

            //  firebaseDatabase = FirebaseDatabase.getInstance();

            biniding.btSubmit.setOnClickListener {

                var name = biniding.etName.text.toString()
                var age=biniding.etAge.text.toString()

                databaseReference = FirebaseDatabase.getInstance().getReference("User")
                // val mRef: DatabaseReference = databaseReference.getReference().child("Users")
                databaseReference!!.push().key
                databaseReference!!.push().key?.let {
                    val user = SendDataFaireBase(it,name,age)
                    databaseReference!!.child("User").setValue(user).addOnCompleteListener {


                        if(it.isSuccessful)
                            Toast.makeText(this, "Add data successfully", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "onCreate: ")

                    }

                }


                *//*  database

                  database.child.setValue(user)?.addOnSuccessListener {

                      database.setValue(user )
                      databaseReference!!.setValue(user);
                      biniding.etName.setText(user.name)
                      biniding.etEmail.setText(user.email)
                      Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()

                  }?.addOnFailureListener{

                      Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


                  }*//*


            }
        }
    }*/
    //        holder.itemView.setOnClickListener {
//            AlertDialog.Builder(context)
//                .setTitle("Delete")
//                .setMessage("Are You Sure you want to Delete your Message")
//                .setPositiveButton("yes"
//                ) { dialog: DialogInterface?, which: Int ->
//                    var database = FirebaseDatabase.getInstance().reference
//                    database.child("chats").child(senderRoom).child("messages")
//                        .child(currentmessage.messageId).removeValue()
//                    database.child("chats").child(receiverRoom).child("messages")
//                        .child(currentmessage.messageId).removeValue()
//                }.setPositiveButton("No",
//                    DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
//                        dialog!!.dismiss()
//                    }).show()
//
//
//        }



//
//    package com.app.googlemap
//
//    import android.Manifest
//    import android.content.Context
//    import android.content.Intent
//    import android.content.pm.PackageManager
//    import android.location.Geocoder
//    import android.location.LocationManager
//    import android.os.Bundle
//    import android.provider.Settings
//    import android.widget.Toast
//    import androidx.appcompat.app.AppCompatActivity
//    import androidx.core.app.ActivityCompat
//    import com.google.android.gms.location.FusedLocationProviderClient
//    import com.google.android.gms.location.LocationServices
//    import com.google.android.gms.maps.CameraUpdateFactory
//    import com.google.android.gms.maps.GoogleMap
//    import com.google.android.gms.maps.OnMapReadyCallback
//    import com.google.android.gms.maps.SupportMapFragment
//    import com.google.android.gms.maps.model.LatLng
//    import com.google.android.gms.maps.model.MarkerOptions
//
//
//    class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
//
//        private lateinit var mMap: GoogleMap
//        private lateinit var fusedLocationClient: FusedLocationProviderClient
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContentView(R.layout.activity_maps)
//            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//            val mapFragment = supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//            mapFragment.getMapAsync(this)
//
//            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        }
//
//        /**
//         * Manipulates the map once available.
//         * This callback is triggered when the map is ready to be used.
//         * This is where we can add markers or lines, add listeners or move the camera. In this case,
//         * we just add a marker near Sydney, Australia.
//         * If Google Play services is not installed on the device, the user will be prompted to install
//         * it inside the SupportMapFragment. This method will only be triggered once the user has
//         * installed Google Play services and returned to the app.
//         */
//        override fun onMapReady(googleMap: GoogleMap) {
//            mMap = googleMap
//
//            if (mMap != null) {
//                getMyCurrentLocation()
//            }
//
//
//            /*  // Add a marker in Sydney and move the camera
//              val sydney = LatLng(23.113307798431876, 72.62708688076961)
//              mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Apollo Hospital"))
//              //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f))*/
//        }
//
//        private fun getMyCurrentLocation() {
//
//            if (checkPermissions()) {
//                // true
//                if (isLocationEnabled()) {
//                    // true
//                    getCordinates()
//
//
//                } else {
//                    // enable location
//                    // location is disabled
//
//                    // location is disabled
//                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                    startActivity(intent)
//                }
//
//
//            } else {
//                // ask for permission
//                requestPermission()
//            }
//
//
//        }
//
//        private fun getCordinates() {
//
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                return
//            }
//            fusedLocationClient.lastLocation.addOnSuccessListener {
//                it?.let {
//                    var lat = it.latitude
//                    var long = it.longitude
//
//                    // Geo-coding
//                    // Geo-coding
//                    val geocoder = Geocoder(applicationContext)
//
//                    var addresses = geocoder.getFromLocation(lat, long, 1)
//                    val myAddress: String =
//                        addresses.get(0).getAddressLine(0).toString() + "," + addresses.get(0)
//                            .getCountryName()
//
//                    val coordinates = LatLng(lat, long)
//                    mMap.addMarker(MarkerOptions().position(coordinates).title(myAddress))
//                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15.0f))
//
//                }
//            }
//
//        }
//
//        private fun requestPermission() {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ),
//                100
//            )
//        }
//
//        private fun checkPermissions(): Boolean {
//            return ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    ) == PackageManager.PERMISSION_GRANTED
//        }
//
//        private fun isLocationEnabled(): Boolean {
//            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//                LocationManager.NETWORK_PROVIDER
//            )
//        }
//
//        override fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<String?>,
//            grantResults: IntArray
//        ) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//            if (requestCode == 100) {
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // user has allow for map access
//                    Toast.makeText(this, "User has allowed ", Toast.LENGTH_SHORT).show()
//                } else {
//
//                    // denied
//                    Toast.makeText(this, "User has denied ", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//







}
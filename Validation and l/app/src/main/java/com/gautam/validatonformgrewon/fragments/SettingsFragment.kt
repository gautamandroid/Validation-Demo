package com.gautam.validatonformgrewon.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gautam.validatonformgrewon.*
import com.gautam.validatonformgrewon.databinding.FragmentSettingsBinding
import com.gautam.validatonformgrewon.entiy.AppDataBase
import com.gautam.validatonformgrewon.shareprefrence.PrefManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay


class SettingsFragment : Fragment() {

    val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            if (permission.keys.contains(Manifest.permission.SEND_SMS) || permission.keys.contains(
                    Manifest.permission.RECEIVE_SMS
                )
            ) {

                if (permission[Manifest.permission.SEND_SMS] == true && permission[Manifest.permission.RECEIVE_SMS] == true) {

                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.smsgranted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.smsdenied),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }


            } else if (permission.keys.contains(Manifest.permission.ACCESS_FINE_LOCATION) || permission.keys.contains(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                if (permission[Manifest.permission.ACCESS_FINE_LOCATION] == true && permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.locationgranted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.locationdenied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (permission.keys.contains(Manifest.permission.READ_EXTERNAL_STORAGE) || permission.keys.contains(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {

                if (permission[Manifest.permission.READ_EXTERNAL_STORAGE] == true && permission[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true) {

                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.storagegranted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.storagedenied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (permission.keys.contains(Manifest.permission.READ_CALENDAR) || permission.keys.contains(
                    Manifest.permission.WRITE_CALENDAR
                )
            ) {

                if (permission[Manifest.permission.READ_CALENDAR] == true && permission[Manifest.permission.WRITE_CALENDAR] == true) {

                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.calenderisgranted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.calenderisdenied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (permission.keys.contains(Manifest.permission.READ_CALL_LOG) || permission.keys.contains(
                    Manifest.permission.WRITE_CALL_LOG
                )
            ) {

                if (permission[Manifest.permission.READ_CALL_LOG] == true && permission[Manifest.permission.READ_CALL_LOG] == true) {

                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.callgranted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.callisdenied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (permission.keys.contains(Manifest.permission.RECORD_AUDIO)) {

                if (permission[Manifest.permission.RECORD_AUDIO] == true) {

                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.microphonegranted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.microphonedenied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    val requestPermissionLauncherr =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.e("TAG", "dddff: " + isGranted)
                Log.e("TAG", "greanteddd: " + ActivityResultContracts.RequestPermission())
                Toast.makeText(requireContext(), "allow new iteam permission", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "don't allow", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "dddmfmredfrm: " + isGranted)
            }
        }


    var requestPermissionAudio =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == true) {
                Log.e("TAG", "audio" + it)
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.audiogranted),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.prmissiondenied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.allownewpermission),
                    Toast.LENGTH_SHORT
                )
            } else {
                Toast.makeText(requireContext(), getString(R.string.dontallow), Toast.LENGTH_SHORT)
            }
        }


    lateinit var binding: FragmentSettingsBinding

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var db: AppDataBase
    lateinit var prefManager: PrefManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseAuth.getInstance().signOut();
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        firebaseAuth = FirebaseAuth.getInstance()
        db = AppDataBase.getInstance(requireContext())
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("509451970679-26oq01c5hpvdmjcr4da95usup116jogc.apps.googleusercontent.com")
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        prefManager = PrefManager(requireContext())

        val loginType = PrefManager(requireContext()).getLoginCredentials()?.loginType
        binding.tvViewtyepset.text = loginType
        Log.e("TAG", "onViewCreated: " + loginType)

        if (account != null) {
            binding.tvSetgoogleemail.text = """personEmail =${account.email}"""
        }
        binding.btPermission.setOnClickListener {
            showOptionDialog()
        }
        binding.btLogout.setOnClickListener {
            PrefManager(requireContext()).clear()
            singout()
        }
        binding.btChangepassworld.setOnClickListener {
            var i = Intent(requireContext(), ChangePassword::class.java)
            startActivity(i)
        }
        binding.btProfile.setOnClickListener {
            var i = Intent(
                requireContext(), ChangeProfile::class.java
            )
            startActivity(i)
        }

        binding.btCurrentlocation.setOnClickListener {
            val i = Intent(requireContext(), MapActivity::class.java)
            startActivity(i)
            // requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.fab.setOnClickListener {

            // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)


            Handler(Looper.getMainLooper()).postDelayed({
                if (prefManager.isNightMode() == true) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    prefManager.saveIsNight(false)
                    val intent  = Intent(requireContext(),SplashActivity::class.java)
                    startActivity(intent)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    prefManager.saveIsNight(true)
                    val intent  = Intent(requireContext(),SplashActivity::class.java)
                    startActivity(intent)
                }

            }, 1000)
        }
    }


    private fun showOptionDialog() {
        val items =
            arrayOf(
                "Text Sms",
                "Your Location",
                "Storage",
                "Calender",
                "Call",
                "Microphone",
                "notification",
                "overlay",
                "All permission give"
            )

        AlertDialog.Builder(requireContext()).setTitle("Select option for mobile Permission")
            .setItems(items) { dialogInterface, i ->

                when (i) {

                    0 -> {
                        if (ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.SEND_SMS
                            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.RECEIVE_SMS
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {

                        } else {

                            requestMultiplePermissions.launch(
                                arrayOf(
                                    Manifest.permission.SEND_SMS,
                                    Manifest.permission.RECEIVE_SMS
                                )
                            )
                        }
                    }
                    1 -> {
                        if (ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {

                        } else {
                            requestMultiplePermissions.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }

                    }
                    2 -> {
                        if (ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {

                        } else {

                            requestMultiplePermissions.launch(
                                arrayOf(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                            )
                        }
                    }
                    3 -> {
                        if (ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.READ_CALENDAR
                            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.WRITE_CALENDAR
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {

                        } else {

                            requestMultiplePermissions.launch(
                                arrayOf(
                                    Manifest.permission.READ_CALENDAR,
                                    Manifest.permission.WRITE_CALENDAR
                                )
                            )
                        }
                    }
                    4 -> {
                        if (ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.READ_CALL_LOG
                            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.WRITE_CALL_LOG
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {

                        } else {

                            requestMultiplePermissions.launch(
                                arrayOf(
                                    Manifest.permission.READ_CALL_LOG,
                                    Manifest.permission.WRITE_CALL_LOG
                                )
                            )
                        }
                    }
                    5 -> {
                        if (ContextCompat.checkSelfPermission(
                                requireActivity(),
                                Manifest.permission.RECORD_AUDIO
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            Log.e("TAG", "RECORD_AUDIO: AUDIO ")
                        } else {
                            requestPermissionAudio.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    }
                    6 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            requestPermissionLauncherr.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                    7 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (!Settings.canDrawOverlays(context)) {
                                val intent = Intent(
                                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:${requireContext().packageName}")
                                )
                                startActivity(intent)
                            }
                        }
                    }
                    8 -> {
                        requestMultiplePermissions.launch(
                            arrayOf(
                                Manifest.permission.READ_CALL_LOG,
                                Manifest.permission.WRITE_CALL_LOG,
                                Manifest.permission.READ_CALENDAR,
                                Manifest.permission.WRITE_CALENDAR,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.SEND_SMS,
                                Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.POST_NOTIFICATIONS

                            )

                        )

                    }

                }

            }.show()

    }


    private fun singout() {
        Firebase.auth.signOut()  //facebook after add
        firebaseAuth.signOut()
        mGoogleSignInClient.signOut()
        val i = Intent(requireContext(), LoginActivity::class.java)
        startActivity(i)
        requireActivity().finish()
        Toast.makeText(
            requireContext(), getString(R.string.logout_successfully), Toast.LENGTH_SHORT
        ).show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {


        if (requestCode == 1) {

            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                if ((ContextCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) ==
                            PackageManager.PERMISSION_GRANTED)
                ) {
                }
            }

        } else if (requestCode == 2) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if ((ContextCompat.checkSelfPermission(
                        requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE

                    ) ==
                            PackageManager.PERMISSION_GRANTED)
                ) {
                }
            }
        } else if (requestCode == 3) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if ((ContextCompat.checkSelfPermission(
                        requireActivity(), Manifest.permission.READ_CALENDAR

                    ) ==
                            PackageManager.PERMISSION_GRANTED)
                ) {
                }
            }
        } else if (requestCode == 4) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if ((ContextCompat.checkSelfPermission(
                        requireActivity(), Manifest.permission.READ_CALL_LOG
                    ) ==
                            PackageManager.PERMISSION_GRANTED)
                ) {
                    Log.e("TAG", "yes call log: " + grantResults)
                }
            }
        } else if (requestCode == 5) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if ((ContextCompat.checkSelfPermission(
                        requireActivity(), Manifest.permission.RECORD_AUDIO
                    ) ==
                            PackageManager.PERMISSION_GRANTED)
                ) {
                    Log.e("TAG", "onRequestPermissionsResult: " + grantResults)
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == 100) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }
        }


    }
}

package com.gautam.validatonformgrewon

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gautam.validatonformgrewon.apimodal.ProfileResponse
import com.gautam.validatonformgrewon.apiretrofit.ApiClient
import com.gautam.validatonformgrewon.databinding.ActivityMainBinding
import com.gautam.validatonformgrewon.fragments.HomeFragment
import com.gautam.validatonformgrewon.fragments.ProfileFragments
import com.gautam.validatonformgrewon.fragments.SettingsFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseAnalytics.getInstance(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        }
        apiUser()

        val data = intent.getIntExtra("the_wolff", 0)
        Log.e("the_walf", "onMainActivtygetvalu:  " + data)

        if (data != null && data != 0) {
            val intentt = Intent(this, DetailActivity::class.java)
            intent.putExtra("ides", data)
            Log.e("the_walf", "onMainActivty:  " + intentt.putExtra("ides",data))

            startActivity(intentt)
        }

        val message = intent.getStringExtra("title")
        val description = intent.getStringExtra("messagebody")
        if (message != null && description != null) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("title", message)
            //intent.putExtra("messagebody", descriptioFn)
            startActivity(intent)
        }
        replaceFragment(HomeFragment(), "Home")
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                var token = it.result
                Log.e("token", "token: " + token)
            }
        }
        binding.bnBottomnavi.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    replaceFragment(HomeFragment(), "Home")
                    true
                }
                R.id.ic_profile -> {
                    replaceFragment(ProfileFragments(), "profils")
                    true
                }
                R.id.ic_seeting -> {
                    replaceFragment(SettingsFragment(), "settings")
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun apiUser() {

        var pro = ApiClient.init(this).getUserList()
        pro.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>, response: Response<ProfileResponse>
            ) {

                if (response.isSuccessful) {
                    Log.e("TAG", "onResponse: " + response.errorBody())
                    Log.e("TAG", "onResponseeee: " + response.body())
                    //  Toast.makeText(this@MainActivity, "Profile sucess", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "unsuccessful profile ", Toast.LENGTH_SHORT)
                    .show()

            }

        })

    }

    //  intent.putExtra("title")
    //  intent.putExtra("messagebody")
    // startActivity(intent)

    private fun replaceFragment(fragment: Fragment, tag: String) {
        var manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fl_wallpeper, fragment, tag)
        transaction.commit()
    }
}

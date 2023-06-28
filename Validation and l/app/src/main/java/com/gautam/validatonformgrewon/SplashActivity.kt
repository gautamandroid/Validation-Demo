package com.gautam.validatonformgrewon

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.gautam.validatonformgrewon.databinding.ActivitySplashBinding
import com.gautam.validatonformgrewon.fragments.HomeFragment
import com.gautam.validatonformgrewon.shareprefrence.PrefManager
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    lateinit var prefManager: PrefManager
    var id: Int = 0
    var ans = false

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getDynamic()

        prefManager = PrefManager(applicationContext)

        if (prefManager.isNightMode() == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
//        Firebase.dynamicLinks.getDynamicLink(intent)
//            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
//                var deepLink: Uri? = null
//                if (pendingDynamicLinkData != null) {
//                    deepLink = pendingDynamicLinkData.link
//                }
//                deepLink?.let { uri ->
//                    val path = uri.toString().substring(deepLink.toString().lastIndexOf("/") + 1)
//
//                    when {
//                        uri.toString().contains("post") -> {
//                            val postId = path.toInt()
//                        }
//                    }
//                }
//            }.addOnFailureListener(this) { e -> Log.e("the_walfe", "getDynamicLink:onFailure", e) }


    }

    private fun getDynamic() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent)
            .addOnSuccessListener(
                this,
                OnSuccessListener<PendingDynamicLinkData>() { pendingDynamicLink: PendingDynamicLinkData? ->

                    var deepLink: Uri? = null
                    if (pendingDynamicLink != null) {
                        //     deepLink=it.link
                        deepLink = pendingDynamicLink.getLink()
                        Log.e("the_wolff", "reciverLink: " + deepLink)

                        val stringLink = deepLink.toString()
                        var lastBit = stringLink.substring(stringLink.lastIndexOf('=') + 1)
                        //  = is not take  +1 is writing other =  is take
                        Log.e(
                            "the_wolff",
                            "reciverLink--0: " + stringLink.substring(stringLink.lastIndexOf('=') + 1)
                        )
                        Log.e(
                            "the_wolff",
                            "reciverLink--9: " + stringLink.substring(stringLink.lastIndexOf('='))
                        )
                        id = lastBit.toInt()
                        Log.e("the_walf", "splash: " + id)
                        Log.e("the_wolff", "onCreate: " + lastBit)

//                    if (id!=null&& id!=0){
//                        val i = Intent(this, MainActivity::class.java)
//                        i.putExtra("the_wolff",id)
//                        Log.e("the_walf", "splashput: " + id)
//
//                        startActivity(i)
//                        finish()
//                    }else{
//                        val i = Intent(this, LoginActivity::class.java)
//                        startActivity(i)
//                        finish()
//                    }

                        // apiCalling()
                        data()

                    } else {
                        data()
                    }

                }).addOnFailureListener(this, OnFailureListener() {
                data()
            })
    }

    private fun data() {
        val name = intent?.getStringExtra("title")

        if (name != null) {
            val description = intent.getStringExtra("messagebody")

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("title", name)
            intent.putExtra("messagebody", description)
            startActivity(intent)
            finish()

        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val prefManagr = PrefManager(this)

                if (id != null && id != 0 && prefManagr.getApi() != null) {
                    val i = Intent(this, MainActivity::class.java)
                    i.putExtra("the_wolff", id)
                    Log.e("the_walf", "splashput: " + id)

                    startActivity(i)
                    finish()
                } else if (prefManagr.getApi() != null) {

                    val i = Intent(this, MainActivity::class.java)
                    // i.putExtra("the_wolff",id)
                    Log.e("the_walf", "splashput: " + id)

                    startActivity(i)
                    finish()
                } else {
                    val i = Intent(this, LoginActivity::class.java)
                    i.putExtra("the_wolff", id)
                    startActivity(i)
                    finish()
                }
            }, 300)

        }
    }
}

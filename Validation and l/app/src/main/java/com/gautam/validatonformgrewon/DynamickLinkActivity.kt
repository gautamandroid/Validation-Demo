package com.gautam.validatonformgrewon

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gautam.validatonformgrewon.databinding.ActivityDynamickLinkBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData

class DynamickLinkActivity : AppCompatActivity() {

    lateinit var binding: ActivityDynamickLinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDynamickLinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseDynamicLinks.getInstance().getDynamicLink(intent)
            .addOnSuccessListener(this, OnSuccessListener<PendingDynamicLinkData>() {

                var deepLink: Uri? = null
                if (it != null) {
                    //     deepLink=it.link
                    deepLink = it.getLink()
                    Log.e("the_wolff", "reciverLink: " + deepLink)
                }

            }).addOnFailureListener(this, OnFailureListener() {

                Log.e("the_wolff", "reciverLink: " + it)

            })

    }
}
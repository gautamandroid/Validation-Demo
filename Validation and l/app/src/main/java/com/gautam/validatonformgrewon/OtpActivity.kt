package com.gautam.validatonformgrewon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gautam.validatonformgrewon.databinding.ActivityOtpBinding
import com.gautam.validatonformgrewon.entiy.AppDataBase
import com.gautam.validatonformgrewon.modal.Users
import com.gautam.validatonformgrewon.shareprefrence.PrefManager
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    lateinit var bindinig: ActivityOtpBinding
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var db: AppDataBase
    lateinit var prefManager: PrefManager
     var number1: String? = null

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            var i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
            Log.e("ottpp", "smsCode: " + credential.smsCode)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.e("ottpp", "FirebaseException: " + e.message)

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken

        ) {
            Log.e("ottpp", "verificationId: " + verificationId)
            storedVerificationId = verificationId

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindinig = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(bindinig.root)
        prefManager = PrefManager(this)
        db = AppDataBase.getInstance(this)
        auth = FirebaseAuth.getInstance()
        clicklistner()

        number1 = intent.getStringExtra("number") ?: " "
        // var user = Users(googlefacebook = number1, loginType = "login by phone ")
        Log.e("number", "signInWithPhoneAuthCredential: " + number1)
        //  prefManager.saveLoginCredentials(user)
        //  db.userDao().insertRecord(user)

        bindinig.btVerifayotp.setOnClickListener {
            val number = intent.getStringExtra("number") ?: " "
            Log.e("numberee", "onCreate: " + number)
            val options = PhoneAuthOptions.newBuilder(auth)

                .setPhoneNumber(number)
                .setTimeout(50L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }


        val options = PhoneAuthOptions.newBuilder(auth)

            .setPhoneNumber(number1 ?: "")
            .setTimeout(50L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        bindinig.btVerifayotp.setOnClickListener {

            val otp = bindinig.otpReciveenter.getStringFromFields().trim()
            if (otp.isNotEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId, otp
                )
                signInWithPhoneAuthCredential(credential)
                credential.provider
                Log.e("provider", "onCreate:" + credential.provider)
                Log.e("ottpp", "onVerificationFailed: " + otp)
                Log.e("ottpp", "onVerificationFailed: " + storedVerificationId)
            }

        }

    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    //   Log.e("TAG", "signInWithPhoneAuthCredential: " + number)

                    val user = Users(googlefacebook = number1, loginType = "login by phone number")
                    Log.e("TAG", "signInWithPhoneAuthCredential: " + number1)

                    db.userDao().insertRecord(user)
                    prefManager.saveLoginCredentials(user)
                    //95127 61151 yash sir mobile number
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid otp", Toast.LENGTH_SHORT).show()
                    }
                }
            }


    }

    private fun clicklistner() {
        bindinig.btBack.setOnClickListener {
            var i = Intent(this, PhoneSmsActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
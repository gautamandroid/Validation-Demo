package com.gautam.validatonformgrewon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gautam.validatonformgrewon.param.ChangePassParam
import com.gautam.validatonformgrewon.apimodal.RegisterResponse
import com.gautam.validatonformgrewon.apiretrofit.ApiClient
import com.gautam.validatonformgrewon.databinding.ActivityChangePasswordBinding
import com.gautam.validatonformgrewon.fragments.SettingsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassword : AppCompatActivity() {

    lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btChangepass.setOnClickListener {
            val oldpassword = binding.etOldepassword.text.toString().trim()
            val newpassword = binding.etNewpassword.text.toString().trim()

            var param = ChangePassParam(
                newPassword = newpassword,
                oldPassword = oldpassword


            )
            passwordChange(param)
        }


    }

    private fun passwordChange(param: ChangePassParam) {
        Log.e("TAG", "passwordChange: " + param)
        var pass = ApiClient.init(this).useChangePasswrd(param)
        Log.e("TAG", "passwordChangwwe: " + pass)
        pass.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.e("TAG", "onResponsepass: " + response)
                Log.e("TAG", "onResponsebody: " + response.body())
                Log.e("TAG", "onResponse: " + response.errorBody())


                if (response.isSuccessful) {

                    Toast.makeText(this@ChangePassword, "Successful password", Toast.LENGTH_SHORT)
                        .show()

                    var i = Intent(this@ChangePassword, SettingsFragment::class.java)
                    startActivity(i)

                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                t.printStackTrace()
                Toast.makeText(this@ChangePassword, "Not successful ", Toast.LENGTH_SHORT).show()
            }

        })

    }

    //private fun passwordChange() {
    //     var pass = ApiClient.init(this).useChangePasswrd()


//        pass.enqueue(object : Callback<ChangePassParam> {
//            override fun onResponse(
//                call: Call<ChangePassParam>,
//                response: Response<ChangePassParam>
//            ) {
//
//                if (response.isSuccessful) {
//
//
//                }
//
//            }
//
//            override fun onFailure(call: Call<ChangePassParam>, t: Throwable) {
//                Toast.makeText(this@ChangePassword, "Not Change password", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//        })

    //  }
}
package com.gautam.validatonformgrewon

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gautam.validatonformgrewon.apimodal.RegisterResponse
import com.gautam.validatonformgrewon.apiretrofit.ApiClient
import com.gautam.validatonformgrewon.databinding.ActivityChangeProfileBinding
import com.gautam.validatonformgrewon.param.UpdateParam
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeProfile : AppCompatActivity() {
    lateinit var binding: ActivityChangeProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btChangepass.setOnClickListener {

            var number = binding.etMobilenumber.text.toString().trim()
            var Bod = binding.etBob.text.toString().trim()
            var name = binding.etChangename.text.toString().trim()

            var paeam = UpdateParam(
                name = name,
                dob = Bod,
                mobile_no = number
            )
            callApi(paeam)

        }


    }

    private fun callApi(paeam: UpdateParam) {

        var profile=ApiClient.init(this).userProfileChange(paeam)
        profile.enqueue(object :Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {

                Log.e("TAG", "change: " + response)
                Log.e("TAG", "onRespochange: " + response.body())
                Log.e("TAG", "onResponseccccc: " + response.errorBody())


                if (response.isSuccessful){
                    Toast.makeText(this@ChangeProfile, "change profile", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@ChangeProfile, "Not ChangeProfile" +
                        "", Toast.LENGTH_SHORT).show()
            }

        })


    }
}
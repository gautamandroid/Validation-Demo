package com.gautam.validatonformgrewon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gautam.validatonformgrewon.databinding.ActivityShowProfileBinding

class ShowProfile : AppCompatActivity() {

    lateinit var binding: ActivityShowProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }
}
package com.gautam.validatonformgrewon

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gautam.validatonformgrewon.databinding.ActivityForgotpassBinding
import com.gautam.validatonformgrewon.entiy.AppDataBase
import com.gautam.validatonformgrewon.modal.Users
import com.gautam.validatonformgrewon.utils.Utils

class ForgotpassActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotpassBinding
    lateinit var db: AppDataBase
    private var isSuccessful = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotpassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDataBase.getInstance(this)
        var user: MutableList<Users>

        binding.ftSave.setOnClickListener {
            val email = binding.logEmail.text.toString().trim()
            val passworld = binding.ftNpassword.text.toString().trim()
            val cpassworld = binding.ftNconforlpassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.logEmail.error = "enter email"
                binding.logEmail.requestFocus()
            } else if (!Utils.isValidEmail(email!!)) {
                binding.logEmail.error = "enter valid email"
                binding.logEmail.requestFocus()
            } else if (passworld.isEmpty()) {
                binding.ftNpassword.error = "create password"
                binding.ftNpassword.requestFocus()
            } else if (!Utils.isValidpassword(passworld)) {
                binding.ftNpassword.error = "uper case with lover case word"
                binding.ftNpassword.requestFocus()
            } else if (cpassworld.isEmpty()) {
                binding.ftConformpassword.error = "enter same password"
                binding.ftConformpassword.requestFocus()
            } else if (cpassworld != passworld) {
                binding.ftNconforlpassword.error = "not match password"
                binding.ftNconforlpassword.requestFocus()
            } else {
                updaterecourd(email, passworld)
                db.userDao().getUserList().observe(this) {
                    for (data in it) {
                        if (data.email == email) {
                            updaterecourd(email, passworld)

                            isSuccessful = true
                            updaterecourd(email, passworld)
                            break
                        } else {
                            binding.logEmail.error = "not match email"
                            binding.logEmail.requestFocus()
                        }
                    }
                }
                if (isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(this, "forgot password successfully ", Toast.LENGTH_SHORT).show()
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    private fun updaterecourd(email: String, passworld: String) {
        db.userDao().updaredata(Email = email, pass = passworld)
    }
}

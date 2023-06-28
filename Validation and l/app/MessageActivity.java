package com.gautam.validatonformgrewon

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gautam.validatonformgrewon.databinding.ActivityMessageBinding
import com.gautam.validatonformgrewon.modal.SendDataFaireBase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MessageActivity : AppCompatActivity() {

    lateinit var biniding: ActivityMessageBinding
    var firebaseDatabase: FirebaseDatabase? = null//
    var databaseReference: DatabaseReference? = null//dbrefrence


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biniding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(biniding.root)

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase!!.getReference("User")

      //  firebaseDatabase = FirebaseDatabase.getInstance();

        biniding.btSubmit.setOnClickListener {

            var name = biniding.etName.text.toString()
            var email=biniding.etEmail.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("User")
            databaseReference!!.push().key
            databaseReference!!.push().key?.let {
                val user = SendDataFaireBase(it,name,email)
                databaseReference!!.child("User").setValue(user).addOnCompleteListener {

                    if(it.isSuccessful)

                        Log.d("TAG", "onCreate: ")
                    else
                        Log.d("TAG", "onCreate: ")

                }

            }


          /*  database

            database.child.setValue(user)?.addOnSuccessListener {

                database.setValue(user )
                databaseReference!!.setValue(user);
                biniding.etName.setText(user.name)
                biniding.etEmail.setText(user.email)
                Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()

            }?.addOnFailureListener{

                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


            }*/


            }
    }
}
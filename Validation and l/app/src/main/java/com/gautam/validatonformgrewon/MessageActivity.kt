package com.gautam.validatonformgrewon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gautam.validatonformgrewon.adapter.MessageAdepter
import com.gautam.validatonformgrewon.databinding.ActivityMessageBinding
import com.gautam.validatonformgrewon.modal.Message
import com.gautam.validatonformgrewon.shareprefrence.PrefManager
import com.google.firebase.database.*
import java.util.*


class MessageActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var biniding: ActivityMessageBinding
    lateinit var msgadepter: MessageAdepter
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    var messageLisrt = mutableListOf<Message>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biniding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(biniding.root)

        initView()
        getData()
        setClicks()

        biniding.btBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        /*      biniding.etMsg.addTextChangedListener(object : TextWatcher {
                  override fun beforeTextChanged(
                      charSequence: CharSequence, start: Int, before: Int, count: Int
                  ) {

                  }

                  override fun onTextChanged(
                      charSequence: CharSequence, start: Int, before: Int, count: Int
                  ) {
                      if (charSequence.isNotEmpty()) {
                          Log.e("TEXT_WATCHER", "onTextChanged: " + charSequence)

                          biniding.ivSent.isEnabled = true
                          biniding.ivSent.visibility = View.VISIBLE

                      } else {
                          Log.e("TEXT_WWWW", "onTextChanged: " + charSequence)
                          biniding.ivSent.isEnabled = false
                          biniding.ivSent.visibility = View.GONE
                      }
                  }

                  override fun afterTextChanged(editable: Editable) {
                      //  Log.e("TEXT_WATCHER", "after text change: "+editable )

                  }
              })*/

    }

    private fun initView() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.reference
        val layout = LinearLayoutManager(this)
        layout.stackFromEnd = true
        msgadepter = MessageAdepter(this)
        biniding.rcvChat.layoutManager = layout
        biniding.rcvChat.adapter = msgadepter


    }

    private fun setClicks() {
        biniding.ivSent.setOnClickListener(this)
    }

    private fun getData() {
        databaseReference.addValueEventListener(object : EventListener, ValueEventListener {
            @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                messageLisrt.clear()
                for (i in snapshot.children) {
                    var msg = i.getValue(Message::class.java)
                    if (msg != null) {
                        messageLisrt.add(msg)
                    }
                }
                biniding.rcvChat.scrollToPosition(messageLisrt.size - 1)
                msgadepter.messageList(messageLisrt)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onClick(v: View?) {
        when (v) {
            biniding.ivSent -> {
                val etdmsg = biniding.etMsg.text.toString()
                if (etdmsg.isNotEmpty()) {
                    val messageId = databaseReference.push().key
                    if (etdmsg.isNotEmpty()) {
                        msgadepter
                        val prefManagr = PrefManager(this)
                        prefManagr.getLoginCredentials()?.email
                        val messageData = Message(
                            "${prefManagr.getLoginCredentials()?.email}", etdmsg, Date().time
                        )
                        firebaseDatabase.getReference(messageId ?: "temp").setValue(messageData)
                    }
                }
                biniding.etMsg.setText("")
            }

        }
    }

}
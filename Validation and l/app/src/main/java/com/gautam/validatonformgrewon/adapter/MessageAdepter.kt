package com.gautam.validatonformgrewon.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gautam.validatonformgrewon.databinding.RecivermessageChatBinding
import com.gautam.validatonformgrewon.databinding.SendmessageChatBinding
import com.gautam.validatonformgrewon.modal.Message
import com.gautam.validatonformgrewon.shareprefrence.PrefManager

class MessageAdepter(
    var context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val prefManagr = PrefManager(context)

    private var msgList = mutableListOf<Message>()

    class SentViewHolder(sent: SendmessageChatBinding) : RecyclerView.ViewHolder(sent.root) {
        var bind = sent
    }

    class ReciverMessage(reciv: RecivermessageChatBinding) : RecyclerView.ViewHolder(reciv.root) {
        var bind = reciv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val prefManagr = PrefManager(context)
        if (viewType == 1) {
            //reciver
            return SentViewHolder(
                SendmessageChatBinding.inflate(
                    LayoutInflater.from(context), parent, false
                )
            )
        } else {
            return ReciverMessage(
                RecivermessageChatBinding.inflate(
                    LayoutInflater.from(context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var currentmessage = msgList[position]

        if (holder is SentViewHolder) {
            holder.bind.tvSentMessage.text = currentmessage.message
            holder.bind.tvSendtime.text =
                convertDate(currentmessage.time.toString(), "dd MMM yyyy hh:mm aa")

        } else if (holder is ReciverMessage) {
            holder.bind.tvRecivermessage.text = currentmessage.message
            holder.bind.tvRecvetime.text =
                convertDate(currentmessage.time.toString(), "dd MMM yyyy hh:mm aa")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = msgList[position]
        if (currentMessage.senderId.equals(prefManagr.getLoginCredentials()?.email)) {
            return 1
        } else {
            return 2
        }

    }

    fun convertDate(dateInMilliseconds: String, dateFormat: String?): String {
        return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    fun messageList(msgList: MutableList<Message>) {
        this.msgList = msgList
        notifyDataSetChanged()

    }


}
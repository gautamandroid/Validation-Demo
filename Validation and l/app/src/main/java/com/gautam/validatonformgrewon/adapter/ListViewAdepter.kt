package com.gautam.validatonformgrewon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.R
import com.gautam.validatonformgrewon.apimodal.HomeResponse
import com.gautam.validatonformgrewon.databinding.FragmentListrecyclerBinding

class ListViewAdepter(var context: Context, var listview: MutableList<HomeResponse>) :
    RecyclerView.Adapter<ListViewAdepter.MyViewHolder>() {

    var listner: OnClickEvent? = null
    interface OnClickEvent{
        fun onClickEvent(id:Int,position: Int)

    }
     fun setOnlistItemClickListener(listener: OnClickEvent) {
        this.listner = listener

    }

    class MyViewHolder(itemView: FragmentListrecyclerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FragmentListrecyclerBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var list = listview[position]
        holder.bind.tvTitel.text = list.title
        holder.bind.rattingPoint.text = list.rating.rate.toString()
        holder.bind.tvDescription.text = list.description
        holder.bind.tvPrice.text = list.price.toString()
        holder.bind.RtRattingbar.rating = list.rating.rate.toFloat()


        Glide.with(context)
            .load(list.image)
            .placeholder(R.drawable.ic_baseline_hourglass_top_24)
            .error(R.drawable.ic_no_image)
            .into(holder.bind.ivTmage)

        holder.itemView.setOnClickListener {
            listner?.onClickEvent(list.id,holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return listview.size
    }

    fun setItems(iteamList: ArrayList<HomeResponse>) {
        this.listview = iteamList
        notifyDataSetChanged()
    }

    fun addData(iteamList: ArrayList<HomeResponse>) {
        this.listview .addAll(iteamList)
        notifyDataSetChanged()
    }
}

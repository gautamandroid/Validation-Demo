package com.gautam.validatonformgrewon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.R
import com.gautam.validatonformgrewon.apimodal.CategoriesResponse
import com.gautam.validatonformgrewon.databinding.IteamlistCircalBinding

class ItemAdapter(var context: Context, var itemlist: MutableList<CategoriesResponse>) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: IteamlistCircalBinding) : RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = IteamlistCircalBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var iteam = itemlist[position]
        holder.bind.tvItemlist.text = iteam.title

        Glide.with(context)
            .load(iteam.image)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_hourglass_top_24)
            .error(R.drawable.ic_no_image)
            .into(holder.bind.iteamCircleimage)
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
    fun setItems(iteamList: ArrayList<CategoriesResponse>) {
        this.itemlist = iteamList
        notifyDataSetChanged()
    }
}

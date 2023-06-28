package com.gautam.validatonformgrewon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.R
import com.gautam.validatonformgrewon.apimodal.CategoriesResponse
import com.gautam.validatonformgrewon.databinding.FragmentShoppingBinding

class ShoppingAdepter(var context: Context, var shoplist: MutableList<CategoriesResponse>) :
    RecyclerView.Adapter<ShoppingAdepter.MyViewHolder>() {

    class MyViewHolder(itemView: FragmentShoppingBinding) : RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FragmentShoppingBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sho = shoplist[position]
        holder.bind.materialTextView.text = sho.category
        holder.bind.tvRatting.text = sho.rating.rate.toString()
        //   holder.bind.ivStar.setImageResource(sho.star)

        Glide.with(context)
            .load(sho.image)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_hourglass_top_24)
            .error(R.drawable.ic_no_image)
            .into(holder.bind.ivThumbnail)
    }

    override fun getItemCount(): Int {
        return shoplist.size
    }

    fun setItems(iteamList: ArrayList<CategoriesResponse>) {
        this.shoplist = iteamList
        notifyDataSetChanged()
    }
}

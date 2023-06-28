package com.gautam.validatonformgrewon.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.R
import com.gautam.validatonformgrewon.apimodal.ItemsMenu
import com.gautam.validatonformgrewon.apimodal.ListResponse
import com.gautam.validatonformgrewon.databinding.ActivityIteamRecyclerBinding

class ListRecyclerAdepter(
    var context: Context,
    var list: ArrayList<ListResponse>,  var item: ArrayList<ItemsMenu.Product>
) : RecyclerView.Adapter<ListRecyclerAdepter.MyViewHolder>() {


    lateinit var Adition: TotalAmount
  //  var item = ArrayList<ItemsMenu.Product>()

    interface TotalAmount {
        fun addamount(arraylist: ArrayList<ListResponse>, position: Int)
        fun removeamount(arraylist: ArrayList<ListResponse>, position: Int)
    }

    fun addAdition(adtion: TotalAmount) {
        this.Adition = adtion
    }

    class MyViewHolder(itemView: ActivityIteamRecyclerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var bind = itemView
    }

//    class RowHolder(itemView: RowItemsBinding) : RecyclerView.ViewHolder(itemView.root) {
//        var binde = itemView
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //  if (viewType == 1) {
        // val bindings = RowItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        //  return RowHolder(bindings)
        // } else {
        val binding =
            ActivityIteamRecyclerBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
        //}
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val blistt = list[position]
        for (i in item) {
            if (blistt.id == i.productId) {
                holder.bind.tvAdd.visibility = View.GONE
                holder.bind.lvCount.visibility = View.VISIBLE
                blistt.quantity = i.quantity.toInt()

            }

        }
        holder.bind.tvTitel.text = blistt.title
        holder.bind.tvDescription.text = blistt.description
        holder.bind.rattingPoint.text = blistt.rating.rate.toString()
        holder.bind.RtRattingbar.rating = blistt.rating.rate.toFloat()
        holder.bind.tvPrice.text = blistt.price.toString()

        Glide.with(context).load(blistt.image).centerCrop().into(holder.bind.ivTmage)


        holder.bind.tvCount.text = "${blistt.quantity}"

        holder.bind.tvAdd.setOnClickListener {
            blistt.quantity = blistt.quantity?.plus(1)!!
            holder.bind.tvCount.text = "${blistt.quantity}"
            showAddButton(holder, blistt, true, context)
            val totalList = list.filter { it.quantity!! > 0 }
            Adition.addamount(ArrayList(totalList), position)
        }
        holder.bind.tvPlus.setOnClickListener {
            blistt.quantity = blistt.quantity?.plus(1)!!
            holder.bind.tvCount.text = "${blistt.quantity}"
            val totalList = list.filter { it.quantity!! > 0 }
            Adition.addamount(ArrayList(totalList), position)
        }

        holder.bind.tvMinus.setOnClickListener {
            if (blistt.quantity != 0) {
                blistt.quantity = blistt.quantity?.minus(1)!!
            }
            if (blistt.quantity!! > 0) holder.bind.tvCount.text = "${blistt.quantity}"
            showAddButton(holder, blistt, false, context)
            val totalList = list.filter { it.quantity!! > 0 }
            Adition.removeamount(ArrayList(totalList), position)
        }
        //  }
    }
//        else if (holder is RowHolder) {
//            Glide.with(context)
//                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVBKE5SqZwvXw7vyMmUQAVG0mMnkLrokZ7Wg&usqp=CAU5++")
//                .centerCrop().into(holder.binde.ivRowimage)
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(iteamList: ArrayList<ListResponse>) {
        this.list = iteamList
        notifyDataSetChanged()
    }

    fun saveData(item: ArrayList<ItemsMenu.Product>) {
        this.item = item
        notifyDataSetChanged()
    }


/* override fun getItemViewType(position: Int): Int {
     return if (list[position].tyepimage == "image") 1 else 2
 }*/

//    override fun getItemCount(): Int {
//        return list.size
//    }

    private fun showAddButton(
        holder: ListRecyclerAdepter.MyViewHolder,
        blist: ListResponse,
        isAdd: Boolean,
        context: Context
    ) {
        val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        val slidDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        val bottomToTop = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top)
        val slidBottom = AnimationUtils.loadAnimation(context, R.anim.slide_bottom)

        if (blist.quantity == 0) {
            holder.bind.tvAdd.visibility = View.VISIBLE
            holder.bind.lvCount.visibility = View.GONE
            holder.bind.lvCount.startAnimation(slidBottom)
            holder.bind.tvAdd.startAnimation(slidDown)
        } else {
            holder.bind.tvAdd.visibility = View.GONE
            holder.bind.lvCount.visibility = View.VISIBLE
            if (isAdd) {
                holder.bind.lvCount.startAnimation(bottomToTop)
                holder.bind.tvAdd.startAnimation(slideUp)
            }
        }
    }
}

package com.gautam.validatonformgrewon.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.R
import com.gautam.validatonformgrewon.apimodal.AutoViewResponse

class AutoAdepter(var context: Context) : PagerAdapter() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private var imagelist = ArrayList<AutoViewResponse>()

    override fun getCount(): Int {
        return imagelist.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var imaLayout = inflater.inflate(R.layout.auto_viewpager, container, false)
        Log.e("TAG", "instantiateItem: " + imaLayout)
        var imageView = imaLayout.findViewById(R.id.Au_auto) as ImageView

        Glide.with(context)
            .load(imagelist[position].image)
            .placeholder(R.drawable.ic_baseline_hourglass_top_24)
            .error(R.drawable.ic_no_image)
            .into(imageView)

        container.addView(imaLayout)
        return imaLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    fun setItems(imagelist: ArrayList<AutoViewResponse>) {
        this.imagelist = imagelist
        notifyDataSetChanged()
    }

}

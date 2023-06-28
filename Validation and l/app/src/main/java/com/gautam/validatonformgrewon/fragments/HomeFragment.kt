package com.gautam.validatonformgrewon.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gautam.validatonformgrewon.DetailActivity
import com.gautam.validatonformgrewon.MessageActivity
import com.gautam.validatonformgrewon.adapter.AutoAdepter
import com.gautam.validatonformgrewon.adapter.ItemAdapter
import com.gautam.validatonformgrewon.adapter.ListViewAdepter
import com.gautam.validatonformgrewon.adapter.ShoppingAdepter
import com.gautam.validatonformgrewon.apimodal.AutoViewResponse
import com.gautam.validatonformgrewon.apimodal.CategoriesResponse
import com.gautam.validatonformgrewon.apimodal.HomeResponse
import com.gautam.validatonformgrewon.apiretrofit.ApiClient
import com.gautam.validatonformgrewon.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeFragment : Fragment(), ListViewAdepter.OnClickEvent {

    lateinit var binding: FragmentHomeBinding
    lateinit var autoadepter: AutoAdepter
    lateinit var itemadepter: ItemAdapter
    lateinit var shoping: ShoppingAdepter

    //lateinit var listview: ListViewAdepter
    lateinit var listViewAdepter: ListViewAdepter
    var currentItem = 0
    var totalItem = 0
    var scrollItem = 0
    var limit = 10
    var TOTAL_ITEM = 20
    var scrolling = false

// var id:Int=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoslieder()
        listViewApi(limit)
        categoriyApi()


        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        listViewAdepter = ListViewAdepter(requireContext(), arrayListOf())
        binding.viList.displayedChild = 0
        binding.revList.layoutManager = manager
        binding.revList.adapter = listViewAdepter

        listViewAdepter.setOnlistItemClickListener(this)
        binding.revList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItem = manager.itemCount
                scrollItem = manager.findFirstCompletelyVisibleItemPosition()
                Log.e("The_Wolf", "currentItem: " + currentItem)
                Log.e("The_Wolf", "TOTAL_ITEM: " + TOTAL_ITEM)
                if (currentItem <= TOTAL_ITEM) {
                    Log.e("The_Wolf", "listViewApi: ")
                    scrolling = true
                    binding.pbProgressbar.isVisible = true
                    listViewApi(limit)

                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    scrolling = false

            }
        })


        binding.imMessage.setOnClickListener {
            var intent = Intent(requireContext(), MessageActivity::class.java)
            startActivity(intent)
        }
//
//        //getshoppingList(requireContext())
//        shoping = ShoppingAdepter(requireContext(), arrayListOf())
//        binding.revShop.layoutManager = LinearLayoutManager(
//            requireContext(),
//            LinearLayoutManager.HORIZONTAL,
//            false
        // )
        binding.revShop.adapter = shoping
    }


    private fun categoriyApi() {

        //getshoppingList(requireContext())
        shoping = ShoppingAdepter(requireContext(), arrayListOf())
        binding.viImagee.displayedChild = 0
        binding.revShop.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.revShop.adapter = shoping
        itemadepter = ItemAdapter(requireContext(), arrayListOf())
        binding.viCircale.displayedChild = 0
        binding.rcvItem.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvItem.adapter = itemadepter


        var cate = ApiClient.listView(requireContext()).getCategories()
        cate.enqueue(object : Callback<ArrayList<CategoriesResponse>> {
            override fun onResponse(
                call: Call<ArrayList<CategoriesResponse>>,
                response: Response<ArrayList<CategoriesResponse>>
            ) {
                Log.e("TAG", "categorisr: " + response)
                Log.e("TAG", "categorisr bodiy: " + response.body())
                Log.e("TAG", "categorisr errorbodiy: " + response.errorBody())

                if (response.isSuccessful) {
                    binding.viCircale.displayedChild = 1
                    binding.viImagee.displayedChild = 1

                    // Toast.makeText(requireContext(), "categories successful", Toast.LENGTH_SHORT)
                    //     .show()
                    Log.e("TAG", "categorisr errorbodiy: " + response.errorBody())

                    val category = response.body()
                    if (category != null) {
                        itemadepter.setItems(category)
                        shoping.setItems(category)
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<CategoriesResponse>>, t: Throwable) {
                t.printStackTrace()
                Log.e("TAG", "onFailure categoriy: " + t.cause)
                binding.viCircale.displayedChild = 2
                binding.viImagee.displayedChild = 2

            }

        })

    }

    private fun listViewApi(limit: Int) {


        var list = ApiClient.listView(requireContext()).getListView(limit)
        list.enqueue(object : Callback<ArrayList<HomeResponse>> {
            override fun onResponse(
                call: Call<ArrayList<HomeResponse>>, response: Response<ArrayList<HomeResponse>>
            ) {

                if (response.isSuccessful) {
                    binding.pbProgressbar.isVisible = false
                    binding.viList.displayedChild = 1
                    //    Toast.makeText(requireContext(), "successful list ", Toast.LENGTH_SHORT).show()

                    var allList = response.body()
                    if (allList != null) {
                        listViewAdepter.addData(allList)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<HomeResponse>>, t: Throwable) {
                //      Toast.makeText(requireContext(), "List is fail", Toast.LENGTH_SHORT).show()
                binding.viList.displayedChild = 2
            }
        })
    }

    private fun autoslieder() {

        autoadepter = AutoAdepter(requireContext())
        binding.viViewpager.displayedChild = 0
        binding.ivViewAutoscroll.adapter = autoadepter
        binding.dotsIndicator.attachTo(binding.ivViewAutoscroll)

        var image = ApiClient.listView(requireContext()).getAutoViewImage()
        image.enqueue(object : Callback<ArrayList<AutoViewResponse>> {
            override fun onResponse(
                call: Call<ArrayList<AutoViewResponse>>,
                response: Response<ArrayList<AutoViewResponse>>
            ) {


                if (response.isSuccessful) {
//                    Toast.makeText(requireContext(), "Viewpager successful", Toast.LENGTH_SHORT)
//                        .show()
                    binding.viViewpager.displayedChild = 1

                    val list = response.body()

                    //    list?.let {
                    if (list != null) {
                        autoadepter.setItems(list)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<AutoViewResponse>>, t: Throwable) {
                // Toast.makeText(requireContext(), "faile image", Toast.LENGTH_SHORT).show()
                binding.viViewpager.displayedChild = 2
            }
        })

        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            if (binding.ivViewAutoscroll.currentItem == autoadepter.count - 1) {
                binding.ivViewAutoscroll.currentItem = 0
            } else {
                binding.ivViewAutoscroll.currentItem++
            }
        }

        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            }, 1000, 2000
        )
    }

    override fun onClickEvent(id: Int, position: Int) {
        var intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("id", id)
        ActivityOptions.makeSceneTransitionAnimation(requireContext() as Activity?)
        //    intent.putExtra("Food", food)
        startActivity(intent)
    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 100) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            }
//        }
//    }
}

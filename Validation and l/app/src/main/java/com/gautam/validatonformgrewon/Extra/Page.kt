//package com.example.paginationn
//
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.AbsListView
//import android.widget.LinearLayout
//import androidx.core.widget.NestedScrollView
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.StaggeredGridLayoutManager
//import com.app.androidnetwork.database.ApiClient
//import com.app.pagination.Dataa
//import com.app.pagination.ItemAdapter
//import com.example.paginationn.databinding.ActivityMainBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class MainActivity : AppCompatActivity() {
//    lateinit var adapter: ItemAdapter
//    lateinit var binding: ActivityMainBinding
//    var page = 1
//    var limit = 10
//    var isScrolling = false
//    var currentItem = 0
//    var totalItem = 0
//    var scrollOutItem = 0
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//       var manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//       // var manager = LinearLayoutManager(this)
//
//        adapter = ItemAdapter(this, mutableListOf())
//        binding.rcvItem.layoutManager =manager
//        binding.rcvItem.adapter = adapter
//        getData(page,limit)
//
//        binding.rcvItem.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                currentItem = manager.childCount
//                totalItem = manager.itemCount
//                scrollOutItem = manager.findFirstCompletelyVisibleItemPositions(null)[0]
//
//                if(isScrolling && (currentItem+ scrollOutItem == totalItem)){
//                    isScrolling=false
//                    page++
//                    getData(page,limit)
//                }
//            }
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                    isScrolling =true
//
//                }
//            }
//        })
//
//    }
//
//
//
//
//    private fun getData(page: Int,limit:Int) {
//        ApiClient.init().getItemList(page,limit).enqueue(object: Callback<MutableList<Dataa>> {
//            override fun onResponse(call: Call<MutableList<Dataa>>, response: Response<MutableList<Dataa>>) {
//                Log.e("TAG", "onResponse: ${response.body()}")
//                if(response.isSuccessful){
//                   // Log.e("TAG", "onResponse: ${response.body()}")
//                    response.body()?.let {
//                        adapter.setItem(it)
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<MutableList<Dataa>>, t: Throwable) {
//
//            }
//
//        })
//    }
//}

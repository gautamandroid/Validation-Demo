package com.gautam.validatonformgrewon

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.adapter.ListRecyclerAdepter
import com.gautam.validatonformgrewon.apimodal.DetailsResponse
import com.gautam.validatonformgrewon.apimodal.ItemsMenu
import com.gautam.validatonformgrewon.apimodal.ListResponse
import com.gautam.validatonformgrewon.apiretrofit.ApiClient
import com.gautam.validatonformgrewon.databinding.ActivityDetailBinding
import com.gautam.validatonformgrewon.databinding.ActivityIteamRecyclerBinding
import com.gautam.validatonformgrewon.databinding.ToastRecyclerBinding
import com.gautam.validatonformgrewon.param.CartParam
import com.gautam.validatonformgrewon.shareprefrence.PrefManager
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.component1
import com.google.firebase.dynamiclinks.ktx.component2
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.kishandonga.csbx.CustomSnackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var adepter: ListRecyclerAdepter
    lateinit var prefManager: PrefManager
    lateinit var quntity: ActivityIteamRecyclerBinding
    var dynamicResponse: DetailsResponse? = null
    var total: Int = 0
    var id: Int = 0

    //   var list= UtilList.getMenuList(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        //  quntity = ActivityIteamRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiCalling()

        id = intent.getIntExtra("id", 0)
        Log.e("the_walf", "onCreatedetails: " + id)

        var call = intent.getIntExtra("ides", 0)
        Log.e("the_walf", "onDeataliss:  " + call)

        if (call != null && call != 0) {
             id=call
            apiCalling()
            Log.e("the_walf", "onDeataliscall:  " + call)
        }

        if (id != null && id != 0) {
            apiCalling()
        }

      //  reciverLink()
        var item = ArrayList<ListResponse>()

        prefManager = PrefManager(this)

        val messagess = intent.getStringExtra("title")
        var description = intent.getStringExtra("messagebody")
        if (messagess != null) {
            Toast.makeText(this, messagess, Toast.LENGTH_SHORT).show()
        }
        // shareItem()
        itemsApiCalling()
        //  getcarts()
        //   val dataList = UtilList.getMenuList(this)


        //   val list = UtilList.getMenuList(this)
        //  2000 - 12 - 31


        /*  var grid = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

          grid.setSpanSizeLookup(object : SpanSizeLookup() {
              override fun getSpanSize(position: Int): Int {
                  return when (adepter.getItemViewType(position)) {
                     2-> 1
                      else -> 2
                  }
              }
          })
           binding.rvMenurecycler.layoutManager =grid
  */

        var userid = prefManager.getApi()?.data?.userId
        Log.e("TAG", "userrriddd: " + userid)

        adepter.addAdition(object : ListRecyclerAdepter.TotalAmount {
            override fun addamount(arraylist: ArrayList<ListResponse>, position: Int) {

                var itemCount = 0
                var totalAmount = 0.0
                for (data in arraylist) {
                    itemCount += data.quantity!!
                    totalAmount += data.price.toDouble().times(data.quantity!!)
                    total = data.quantity!!
                    id = data.id!!
                    Log.e("TAG", "total iteam++: " + total)
                    Log.e("TAG", "total id++: " + id)

                }

                item = arraylist
                val layout = ToastRecyclerBinding.inflate(layoutInflater)
                layout.tvTotal.text = totalAmount.toString()
                layout.tvItem.text = itemCount.toString()

                CustomSnackbar(this@DetailActivity).show {
                    customView(layout.root)
                    duration(Snackbar.LENGTH_INDEFINITE)
                }

                var prodictId: Int = 0
                var quantity: Int = 0
                for (i in item) {
                    prodictId = i.id!!
                    quantity = i.quantity!!
                }
                val productList = ArrayList<CartParam.Product>()
                productList.add(CartParam.Product(productId = prodictId, quantity = quantity))
                val product = CartParam(
                    date = "", id = "", products = productList, userId = userid
                )
                Log.e("TAG", "adde: " + product)

                //    val product =ArrayList<CartParam>()
                //  product.add(CartParam(date = "", id = "", products = productList, userId = userid))

                apiProductList(product)
            }

            override fun removeamount(
                arraylist: ArrayList<ListResponse>, position: Int
            ) {
                var itemCount = 0
                var totalAmount = 0.0
                for (data in arraylist) {
                    itemCount += data.quantity!!
                    totalAmount += data.price.toDouble().times(data.quantity!!)
                    total = data.quantity!!
                    id = data.id!!
                    Log.e("TAG", "total iteam--: " + total)
                    Log.e("TAG", "total idd--: " + id)

                }

                item = arraylist
                val layout = ToastRecyclerBinding.inflate(layoutInflater)
                layout.tvTotal.text = totalAmount.toString()
                layout.tvItem.text = itemCount.toString()
                CustomSnackbar(this@DetailActivity).show {
                    customView(layout.root)
                    duration(Snackbar.LENGTH_SHORT)
                }
                /* for (i in arraylist) {
                     var que = i.quantity
                     var prodId = i.id
                 } */
                var prodictId: Int = 0
                var quantity: Int = 0
                for (i in item) {
                    prodictId = i.id!!
                    quantity = i.quantity!!
                }
                val productList = ArrayList<CartParam.Product>()
                productList.add(CartParam.Product(productId = prodictId, quantity = quantity))

                val product = CartParam(
                    date = "", id = "", products = productList, userId = userid
                )
                Log.e("TAG", "remov: " + product)
                apiProductList(product)

            }

        })

    }

    private fun reciverLink() {

        FirebaseDynamicLinks.getInstance().getDynamicLink(intent)
            .addOnSuccessListener(this, OnSuccessListener<PendingDynamicLinkData>() {

                var deepLink: Uri? = null
                if (it != null) {
                    //     deepLink=it.link
                    deepLink = it.getLink()
                    Log.e("the_wolff", "reciverLink: " + deepLink)


                    val stringLink = deepLink.toString()
                    var lastBit = stringLink.substring(stringLink.lastIndexOf('=') + 1)
                    //  = is not take  +1 is writing other =  is take
                    Log.e(
                        "the_wolff",
                        "reciverLink--0: " + stringLink.substring(stringLink.lastIndexOf('=') + 1)
                    )
                    Log.e(
                        "the_wolff",
                        "reciverLink--9: " + stringLink.substring(stringLink.lastIndexOf('='))
                    )
                    id = lastBit.toInt()
                    if (id != null && id != -1) {
                        apiCalling()
                    }
                }

            }).addOnFailureListener(this, OnFailureListener() {


            })

    }


    private fun shareItem() {

        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
//            .setLink(Uri.parse("https://grewon.com/" + BuildConfig.APPLICATION_ID + BuildConfig.VERSION_CODE  ))
            .setLink(Uri.parse("https://www.grewon.com/?productId=${id}"))
            .setDomainUriPrefix("https://validatonformgrewon.page.link/")

            //?;redfral=999
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            .setSocialMetaTagParameters(
                DynamicLink.SocialMetaTagParameters.Builder()
                    .setTitle(dynamicResponse!!.title)
                    .setDescription(dynamicResponse!!.description)
                    .setImageUrl(dynamicResponse!!.image!!.toUri())
                    .build()
            )
            .buildDynamicLink()



        Log.e("the_wolf", "shareItem: " + dynamicLink)
        Log.e("the_wolf", "shareItem: " + dynamicLink.uri)
        var dynamicLinkUri = dynamicLink.uri


        Firebase.dynamicLinks.shortLinkAsync {

            longLink = Uri.parse(dynamicLinkUri.toString())
        }.addOnSuccessListener { (shortLink, flowChartLink) ->


            if (shortLink != null) {
                dynamicLinkUri = shortLink

            }

        }.addOnFailureListener {
            // Error
            // ...
        }

        binding.shareIteam.setOnClickListener {
            // shareItem()
//            val builder = Uri.Builder()
//                .scheme("https")
//                .authority(appCode + ".app.goo.gl")
//                .path("/")
//                .appendQueryParameter("link", deepLink)
//                .appendQueryParameter("com.gautam.validatonformgrewon", packageName)


            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setAction(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
            shareIntent.putExtra(Intent.EXTRA_TEXT, dynamicLinkUri.toString())
            shareIntent.type = "text/plain"
            // shareIntent.setType("image/*")
            //  startActivity(Intent.createChooser(shareIntent, "Share Image"));
            //shareItem()
            startActivity(shareIntent)
            Log.e("the_wolf", "shareItem: " + dynamicLink)


        }

    }

    private fun getcarts() {
        val carts = ApiClient.listView(this).getCarts()
        carts.enqueue(object : Callback<ArrayList<ItemsMenu>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsMenu>>, response: Response<ArrayList<ItemsMenu>>
            ) {
                Log.e("TAG", "items: " + response)
                Log.e("TAG", "itemss: " + response.body())
                Log.e("TAG", "jesonnitem: " + Gson().toJson(response.body()))
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        // Log.e("TAG", "dattatta: "+data )
                        //   if (data!=null){
                        val iteam = response.body()

                        if (iteam != null) {
                            for (i in iteam) {
                                if (i.id == 1) {
                                    adepter.saveData(i.products)

                                }
                            }
                        }
                    }

                    Toast.makeText(this@DetailActivity, "Iteam ", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ArrayList<ItemsMenu>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun itemsApiCalling() {
        adepter = ListRecyclerAdepter(this, arrayListOf(), arrayListOf())
        binding.vitList.displayedChild = 0
        binding.rvMenurecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMenurecycler.adapter = adepter

        val items = ApiClient.listView(this).getItem()
        items.enqueue(object : Callback<ArrayList<ListResponse>> {
            override fun onResponse(
                call: Call<ArrayList<ListResponse>>, response: Response<ArrayList<ListResponse>>
            ) {
                val item = response.body()
                if (item != null) {
                    adepter.setItems(item)
                }
                if (response.isSuccessful) {
                    binding.vitList.displayedChild = 1


                    Toast.makeText(this@DetailActivity, "ADD items Successful ", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ArrayList<ListResponse>>, t: Throwable) {
                t.printStackTrace()
                binding.vitList.displayedChild = 2

            }

        })
    }

    private fun apiProductList(product: CartParam) {
        val api = ApiClient.listView(this).postCarts(product)
        api.enqueue(object : Callback<ItemsMenu> {
            override fun onResponse(
                call: Call<ItemsMenu>, response: Response<ItemsMenu>
            ) {
                var product = response.body()
                Log.e("TAG", "productlist: " + response.errorBody())
                Log.e("TAG", "productbodiy: " + response.body())

                if (response.isSuccessful) {
                    Toast.makeText(this@DetailActivity, "product successful", Toast.LENGTH_SHORT)
                        .show()

                }

            }

            override fun onFailure(call: Call<ItemsMenu>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@DetailActivity, "false successful", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "onFailureee: " + t.cause)
            }

        })

    }


    private fun apiCalling() {
        val call = ApiClient.listView(this).getCatreUp(id)
        Log.e("TAG", "callingggg: " + id)
        Log.e("TAG", "caaalin: " + call)
        call.enqueue(object : Callback<DetailsResponse> {
            override fun onResponse(
                call: Call<DetailsResponse>, response: Response<DetailsResponse>
            ) {

                if (response.isSuccessful) {

                    if (response.body() != null) {


                        var data = response.body()
                        binding.tvBurgurking.text = data?.title
                        binding.tvDescritons.text = data?.description
                        binding.RtRattingbar.rating = data?.rating?.rate?.toFloat()!!
                        binding.rattingPoint.text = data.rating.rate.toString()

                        dynamicResponse = data
                        Log.e("TAG", "onResponse: " + dynamicResponse)

                        shareItem()
                        if (data != null) {
                            Glide.with(this@DetailActivity).load(data.image)
                                .placeholder(R.drawable.ic_baseline_hourglass_top_24)
                                .error(R.drawable.ic_no_image).into(binding.ivBthumbnail)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}

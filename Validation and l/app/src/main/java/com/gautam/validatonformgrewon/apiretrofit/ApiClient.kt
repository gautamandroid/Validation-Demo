package com.gautam.validatonformgrewon.apiretrofit

import android.content.Context
import android.util.Log
import com.gautam.validatonformgrewon.shareprefrence.PrefManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

    companion object {

        private var retrofit: Retrofit? = null
        private var retrofit1: Retrofit? = null
        private var retrofit2:Retrofit?=null
        lateinit var prefManager: PrefManager

        fun init(context: Context): ApiService {
            val httpClient = Retrofit.Builder()

//            if (BuildConfig.DEBUG) {
//                val logging = HttpLoggingInterceptor()
//                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//                //httpClient.addInterceptor(logging)
//            }
            prefManager = PrefManager(context)
            if (retrofit == null) {

                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level=(HttpLoggingInterceptor.Level.BODY)
                    var client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->

                        val original = chain.request()
                        // Request customization: add request headers
                        // Request customization: add request headers
                        val requestBuilder: Request.Builder = original.newBuilder()
                            .header(
                                "X-Auth-Token",
                                "${prefManager.getToken().toString()}"

                            ) // <-- this is the important line
                        Log.e("TAG", "intercept: " + prefManager.getToken())
                        Log.e("TAG", "interccccept: " + requestBuilder)

                        val request: Request = requestBuilder.build()
                        chain.proceed(request)
                    }).addInterceptor(interceptor).build()




                    retrofit = Retrofit.Builder()
                        .baseUrl(Link.API_LINk)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()

                }


            return retrofit!!.create(ApiService::class.java)

        }



        fun listView(context: Context): ApiService {
            prefManager = PrefManager(context)
            if (retrofit2 == null) {

                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                var client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                    val original = chain.request()
                    // Request customization: add request headers
                    // Request customization: add request headers
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .header(
                            "X-Auth-Token",
                            "${prefManager.getToken().toString()}"

                        ) // <-- this is the important line
                    Log.e("TAG", "intercept: " + prefManager.getToken())
                    Log.e("TAG", "interccccept: " + requestBuilder)

                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                }).addInterceptor(interceptor).build()

                retrofit2 = Retrofit.Builder()
                    .baseUrl(Link.BASE_URl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()


            }

            return retrofit2!!.create(ApiService::class.java)

        }


    }

}

package com.gautam.validatonformgrewon.param


import com.google.gson.annotations.SerializedName


data class CartParam(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("products")
    val products: ArrayList<Product>,
    @SerializedName("userId")
    val userId: Int?
) {
    data class Product(
        @SerializedName("productId")
        val productId: Int,
        @SerializedName("quantity")
        val quantity: Int
    )
}
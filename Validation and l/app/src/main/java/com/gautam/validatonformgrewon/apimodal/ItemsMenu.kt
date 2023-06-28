package com.gautam.validatonformgrewon.apimodal


import com.google.gson.annotations.SerializedName


data class ItemsMenu(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("products")
    val products: ArrayList<Product>,
    @SerializedName("userId")
    val userId: String
) {
    data class Product(
        @SerializedName("productId")
        val productId: Int,
        @SerializedName("quantity")
        val quantity: String
    )
}
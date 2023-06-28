package com.gautam.validatonformgrewon.apimodal


import com.google.gson.annotations.SerializedName

data class DetailsResponse(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("title")
    val title: String,
    var quantity:Int?=0
) {

    data class Rating(
        @SerializedName("count")
        val count: Int,
        @SerializedName("rate")
        val rate: Double
    )
}
package com.example.fiulostandfound.data

import com.google.gson.annotations.SerializedName

data class Item(

    @SerializedName("image_url")
    val image_url: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("id")
    val id: Long? = null
)
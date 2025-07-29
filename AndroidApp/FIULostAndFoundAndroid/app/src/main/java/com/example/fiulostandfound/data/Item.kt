package com.example.fiulostandfound.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Item(

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("location")
    val location: String?  = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("claimed")
    val claimed: Boolean = false
): Parcelable
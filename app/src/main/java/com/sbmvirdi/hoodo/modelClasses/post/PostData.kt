package com.sbmvirdi.hoodo.modelClasses.post

import com.google.gson.annotations.SerializedName
import com.sbmvirdi.hoodo.modelClasses.owner.Owner

data class PostData(
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: String,
    @SerializedName("publishDate") val publishDate: String,
    @SerializedName("text") val text: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("link") val link: String,
    @SerializedName("likes") val like: Int,
    @SerializedName("owner") val owner: Owner
)
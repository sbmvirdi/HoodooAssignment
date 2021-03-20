package com.sbmvirdi.hoodo.modelClasses.post

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("data") val data: List<PostData>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offSet: Int
)
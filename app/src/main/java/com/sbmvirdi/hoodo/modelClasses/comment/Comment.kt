package com.sbmvirdi.hoodo.modelClasses.comment

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("data") val data: List<CommentData>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offSet: Int
)
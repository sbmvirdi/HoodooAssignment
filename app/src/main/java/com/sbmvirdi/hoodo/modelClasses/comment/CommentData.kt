package com.sbmvirdi.hoodo.modelClasses.comment

import com.google.gson.annotations.SerializedName
import com.sbmvirdi.hoodo.modelClasses.owner.Owner

data class CommentData(
    @SerializedName("owner") val owner: Owner,
    @SerializedName("id") val id: String,
    @SerializedName("message") val message: String,
    @SerializedName("publishDate") val publishDate: String,
    @SerializedName("offset") val offSet: Int
)
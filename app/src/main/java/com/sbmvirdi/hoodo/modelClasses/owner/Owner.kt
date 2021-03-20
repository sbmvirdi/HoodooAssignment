package com.sbmvirdi.hoodo.modelClasses.owner

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("title") val title: String,
    @SerializedName("lastName") val lastName: String
)
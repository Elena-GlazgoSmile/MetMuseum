package ru.glazgosmile.metmuseum.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ObjectsResponseDto(
    @SerializedName("total") val total: Int,
    @SerializedName("objectIDs") val objectIDs: List<Int>?
)
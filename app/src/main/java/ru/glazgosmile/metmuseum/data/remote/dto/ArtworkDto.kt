package ru.glazgosmile.metmuseum.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArtworkDto(
    @SerializedName("objectID") val objectID: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("artistDisplayName") val artistDisplayName: String?,
    @SerializedName("objectDate") val objectDate: String?,
    @SerializedName("primaryImageSmall") val primaryImageSmall: String?,
    @SerializedName("primaryImage") val primaryImage: String?,
    @SerializedName("medium") val medium: String?,
    @SerializedName("dimensions") val dimensions: String?,
    @SerializedName("creditLine") val creditLine: String?,
    @SerializedName("department") val department: String?,
    @SerializedName("culture") val culture: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("classification") val classification: String?
)
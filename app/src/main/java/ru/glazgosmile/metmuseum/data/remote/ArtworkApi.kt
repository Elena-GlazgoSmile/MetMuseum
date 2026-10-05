package ru.glazgosmile.metmuseum.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.glazgosmile.metmuseum.data.remote.dto.ArtworkDto
import ru.glazgosmile.metmuseum.data.remote.dto.ObjectsResponseDto

interface ArtworkApi {

    @GET("public/collection/v1.1/search")
    suspend fun searchArtworks(
        @Query("q") query: String,
        @Query("hasImages") hasImages: Boolean = true,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): ObjectsResponseDto

    @GET("public/collection/v1/objects/{objectID}")
    suspend fun getArtworkById(
        @Path("objectID") objectID: Int
    ): ArtworkDto
}
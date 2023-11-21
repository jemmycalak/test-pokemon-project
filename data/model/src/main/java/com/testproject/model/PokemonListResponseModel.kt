package com.testproject.model

import com.google.gson.annotations.SerializedName

/**
 * @author Jemmy
 * Created on 10/10/2023 at 15:28.
 */
data class PokemonListResponseModel(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("results")
    val results: List<PokemonResponseModel> = emptyList()
)
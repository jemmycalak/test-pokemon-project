package com.testproject.pokemonapp.data.model

/**
 * @author Jemmy
 * Created on 10/10/2023 at 15:28.
 */
data class PokemonListResponseModel(
    val count: Int,
    val results: List<PokemonResponseModel>
)
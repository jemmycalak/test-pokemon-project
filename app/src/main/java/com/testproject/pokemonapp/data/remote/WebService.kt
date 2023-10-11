package com.testproject.pokemonapp.data.remote

import com.testproject.pokemonapp.data.model.Pokemon
import com.testproject.pokemonapp.data.model.PokemonListResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: String,
    ): Response<Pokemon>

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<PokemonListResponseModel>
}

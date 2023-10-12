package com.testproject.pokemonusecase

import com.testproject.model.Pokemon
import com.testproject.model.PokemonListResponseModel
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonapp.core.Resource

interface PokemonRepository {
    suspend fun fetchPokemonById(id: String): Resource<Pokemon>
    suspend fun getPokemonList(offset: Int, limit: Int): Resource<PokemonListResponseModel>
    suspend fun getPokemonListHistory(): List<PokemonResponseModel>
    suspend fun insertPokemon(pokemon: PokemonResponseModel)
    suspend fun deletePokemon(id: Int)
}

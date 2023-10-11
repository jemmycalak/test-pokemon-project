package com.testproject.pokemonapp.data.repository

import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonapp.data.model.Pokemon
import com.testproject.pokemonapp.data.model.PokemonListResponseModel
import com.testproject.pokemonapp.data.model.PokemonResponseModel

interface PokemonRepository {
    suspend fun fetchPokemonById(id: String): Resource<Pokemon>
    suspend fun getPokemonList(offset: Int, limit: Int): Resource<PokemonListResponseModel>
    suspend fun getPokemonListHistory(): List<PokemonResponseModel>
    suspend fun insertPokemon(pokemon: PokemonResponseModel)
    suspend fun deletePokemon(id: Int)
}

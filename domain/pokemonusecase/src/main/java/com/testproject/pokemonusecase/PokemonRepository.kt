package com.testproject.pokemonusecase

import com.testproject.model.Pokemon
import com.testproject.model.PokemonListResponseModel
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonapp.core.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemonById(id: String): Resource<Pokemon>
    fun getPokemonList(offset: Int, limit: Int): Flow<Resource<PokemonListResponseModel>>
    suspend fun getPokemonListHistory(): List<PokemonResponseModel>
    suspend fun insertPokemon(pokemon: PokemonResponseModel)
    suspend fun deletePokemon(id: Int)
}

package com.testproject.pokemonapp.data.repository

import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonapp.data.model.Pokemon
import com.testproject.pokemonapp.data.model.PokemonListResponseModel
import com.testproject.pokemonapp.data.model.PokemonResponseModel
import com.testproject.pokemonapp.data.remote.PokemonDataSource
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDataSource: PokemonDataSource,
) : PokemonRepository {

    override suspend fun fetchPokemonById(id: String): Resource<Pokemon> {
        return pokemonDataSource.fetchPokemonById(id)
    }

    override suspend fun getPokemonList(
        offset: Int,
        limit: Int,
    ): Resource<PokemonListResponseModel> {
        return pokemonDataSource.getPokemonList(offset, limit)
    }

    override suspend fun getPokemonListHistory(): List<PokemonResponseModel> {
        return pokemonDataSource.getPokemonHistory()
    }

    override suspend fun insertPokemon(pokemon: PokemonResponseModel) {
        pokemonDataSource.insertPokemon(pokemon)
    }

    override suspend fun deletePokemon(id: Int) {
        pokemonDataSource.deletePokemon(id)
    }
}

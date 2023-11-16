package com.testproject.pokemonusecase

import com.testproject.datasource.PokemonDataSource
import com.testproject.model.Pokemon
import com.testproject.model.PokemonListResponseModel
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonapp.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDataSource: PokemonDataSource,
) : PokemonRepository {

    override suspend fun fetchPokemonById(id: String): Resource<Pokemon> {
        return pokemonDataSource.fetchPokemonById(id)
    }

    override fun getPokemonList(
        offset: Int,
        limit: Int,
    ): Flow<Resource<PokemonListResponseModel>> {
        return flow {
            emit(pokemonDataSource.getPokemonList(offset, limit))
        }
    }

    override suspend fun getPokemonListHistory(): List<PokemonResponseModel> {
        return pokemonDataSource.getPokemonHistory()
    }

    override suspend fun insertPokemon(pokemon: PokemonResponseModel) {
        pokemonDataSource.insertPokemon(pokemon)
    }

    override suspend fun upsertPokemon(pokemon: PokemonResponseModel) {
        pokemonDataSource.upsertPokemon(pokemon)
    }

    override suspend fun deletePokemon(id: Int) {
        pokemonDataSource.deletePokemon(id)
    }
}

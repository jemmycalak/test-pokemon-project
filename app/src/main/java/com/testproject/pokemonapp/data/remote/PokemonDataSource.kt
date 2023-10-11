package com.testproject.pokemonapp.data.remote

import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonapp.data.local.dao.PokemonDAO
import com.testproject.pokemonapp.data.model.Pokemon
import com.testproject.pokemonapp.data.model.PokemonListResponseModel
import com.testproject.pokemonapp.data.model.PokemonResponseModel
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val webService: WebService,
    private val pokemonDAO: PokemonDAO,
) {
    suspend fun getPokemonList(offset: Int, limit: Int): Resource<PokemonListResponseModel> {
        return try {
            val response = webService.getPokemonList(offset, limit)
            when {
                response.isSuccessful -> {
                    Resource.Success(response.body()!!)
                }
                else -> {
                    Resource.Failure(response.message())
                }
            }
        } catch (e: Exception) {
            Resource.Failure(e.message.orEmpty())
        }
    }

    suspend fun fetchPokemonById(id: String): Resource<Pokemon> {
        return try {
            val response = webService.getPokemonById(id)
            return when {
                response.isSuccessful -> {
                    Resource.Success(response.body()!!)
                }
                else -> {
                    Resource.Failure(response.message())
                }
            }
        } catch (e: Exception) {
            Resource.Failure(e.message.orEmpty())
        }
    }

    suspend fun getPokemonHistory(): List<PokemonResponseModel> {
        return pokemonDAO.getAll()
    }

    suspend fun insertPokemon(pokemon: PokemonResponseModel) {
        pokemonDAO.insert(pokemon)
    }

    suspend fun deletePokemon(id: Int) {
        pokemonDAO.deleteById(id)
    }
}

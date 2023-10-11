package com.testproject.pokemonapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.testproject.pokemonapp.data.model.PokemonResponseModel

@Dao
interface PokemonDAO {
    @Upsert
    suspend fun insert(posts: PokemonResponseModel)

    @Query("SELECT * FROM pokemon_list")
    suspend fun getAll(): List<PokemonResponseModel>

    @Query("DELETE FROM pokemon_list WHERE id=:id")
    suspend fun deleteById(id: Int)
}

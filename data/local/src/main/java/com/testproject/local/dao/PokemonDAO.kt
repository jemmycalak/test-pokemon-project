package com.testproject.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.testproject.model.PokemonResponseModel

@Dao
interface PokemonDAO {
    @Insert
    suspend fun insert(posts: PokemonResponseModel)
    @Upsert
    suspend fun upsert(posts: PokemonResponseModel)

    @Query("SELECT * FROM pokemon_list")
    suspend fun getAll(): List<PokemonResponseModel>

    @Query("DELETE FROM pokemon_list WHERE id=:id")
    suspend fun deleteById(id: Int)
}

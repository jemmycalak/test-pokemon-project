package com.testproject.pokemonapp.di

import android.content.Context
import com.testproject.pokemonapp.data.local.PokemonDB
import com.testproject.pokemonapp.data.local.dao.PokemonDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * @author Jemmy
 * Created on 11/10/2023 at 11:03.
 */
@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    fun provideDataLocal(@ApplicationContext context: Context): PokemonDB {
        return PokemonDB.getInstance(context)
    }

    @Provides
    fun providePokemonDao(database: PokemonDB): PokemonDAO {
        return database.pokemonDAO()
    }
}

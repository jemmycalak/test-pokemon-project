package com.testproject.pokemonapp.di

import com.testproject.pokemonapp.data.repository.PokemonRepositoryImpl
import com.testproject.pokemonapp.data.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @Binds
    abstract fun dataSource(impl: PokemonRepositoryImpl): PokemonRepository
}
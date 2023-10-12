package com.testproject.pokemonapp.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonapp.data.model.Pokemon
import com.testproject.pokemonapp.data.repository.PokemonRepository
import com.testproject.pokemonlist.ui.pokemondetail.DetailPokemonViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

class PokemonViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailPokemonViewModel

    @RelaxedMockK
    private lateinit var repository: PokemonRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = DetailPokemonViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun should_ReturnResourceSuccess_When_IsCorrectPokemonResult() = runTest {
        //Given
        coEvery { repository.fetchPokemonById("2") } returns Resource.Success(Pokemon())
        //When
        val result = viewModel.loadPokemon.getOrAwaitValue()
        //Then
        Assert.assertTrue(result is Resource.Success)
    }

    @Test
    fun should_ReturnResourceFailure_When_IsErrorPokemonResult() = runTest {
        //Given
        coEvery { repository.fetchPokemonById("") } returns Resource.Failure(Exception())
        //When
        val value = viewModel.loadPokemon.getOrAwaitValue()
        //Then
        Assert.assertTrue(value is Resource.Failure)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}
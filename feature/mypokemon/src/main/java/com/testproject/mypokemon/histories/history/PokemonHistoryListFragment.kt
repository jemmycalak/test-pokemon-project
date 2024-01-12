package com.testproject.mypokemon.histories.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.testproject.core.extention.getImageUrl
import com.testproject.core.templete.MainTemplate
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.model.PokemonResponseModel
import com.testproject.mypokemon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPokemon(
    modifier: Modifier = Modifier,
    viewModel: PokemonHistoryListViewModel = hiltViewModel(),
    onPokeminItemClicked: (PokemonResponseModel, Boolean) -> Unit,
) {
    val pokemonData by viewModel.event.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.getPokemonList()
    }
    PokemonHistory(
        modifier = modifier,
        pokemonData = pokemonData,
        onPokeminItemClicked = onPokeminItemClicked,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonHistory(
    modifier: Modifier = Modifier,
    pokemonData: HistoryPokemonEvent,
    onPokeminItemClicked: (PokemonResponseModel, Boolean) -> Unit = { pokemonData, isCatch -> },
) {
    MainTemplate(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.title_pokemonhistory))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
            )
        },
        content = {
            Column(
                modifier = modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (pokemonData) {
                    HistoryPokemonEvent.OnEmptyData -> ShowEmptyData()
                    is HistoryPokemonEvent.OnSuccess -> {
                        ShowPokemonHistory(
                            modifier = modifier,
                            data = pokemonData.data,
                            onPokeminItemClicked = onPokeminItemClicked,
                        )
                    }

                    else -> Unit
                }
            }
        },
    )
}

@Composable
private fun ShowPokemonHistory(
    modifier: Modifier = Modifier,
    data: List<PokemonResponseModel>,
    onPokeminItemClicked: (PokemonResponseModel, Boolean) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(data) { pokemon ->
            PokemonItem(
                modifier = modifier,
                pokemon = pokemon,
                onPokeminItemClicked = {
                    onPokeminItemClicked(it, true)
                },
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonItem(
    modifier: Modifier = Modifier,
    pokemon: PokemonResponseModel,
    onPokeminItemClicked: (PokemonResponseModel) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable(enabled = true, onClick = { onPokeminItemClicked(pokemon) }),
    ) {
        Column(modifier = modifier.align(Alignment.CenterHorizontally)) {
            GlideImage(
                model = pokemon.url.getImageUrl(),
                contentDescription = pokemon.name,
                modifier.align(Alignment.CenterHorizontally),
            )
            Text(
                text = pokemon.name,
                modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 4.dp),
            )
        }
    }
}

@Composable
private fun ShowEmptyData(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Pokemon Kamu Masih Kosong")
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPokemonPreview() {
    PokemonAppTheme {
        PokemonHistory(pokemonData = HistoryPokemonEvent.OnEmptyData)
    }
}

package com.testproject.pokemonlist.ui.pokemonlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.testproject.core.component.LoadingWheel
import com.testproject.core.extention.getIdFromUrl
import com.testproject.core.extention.getImageUrl
import com.testproject.core.templete.MainTemplate
import com.testproject.core.theme.Dimens
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonlist.R

@Composable
internal fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel(),
    onPokeminItemClicked: (PokemonResponseModel) -> Unit = {},
    showActionBar: (String, String?) -> Unit = { _, _ -> },
) {
    val pokemonData by viewModel.pokemonData.collectAsStateWithLifecycle(
        initialValue = PokemonListEvent.OnLoading,
    )
    val keyword by viewModel.keyword
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(pokemonData) {
        when (pokemonData) {
            PokemonListEvent.OnNetworkError -> {
                showActionBar("Failed load data", null)
            }

            else -> Unit
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getPokemonList(keyword, 0)
    }

    MainTemplate(
        modifier = modifier,
        topBar = {
            SearchBar(
                query = keyword,
                onQueryChange = viewModel::searchPokemon,
                modifier = Modifier
                    .background(Color.White)
                    .focusRequester(focusRequester),
            )
        },
        content = {
            Column(
                modifier = modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (pokemonData) {
                    is PokemonListEvent.OnLoading -> {
                        ShowLoading(modifier)
                    }

                    is PokemonListEvent.OnSuccess -> {
                        ShowPokemonList(
                            modifier = modifier,
                            data = (pokemonData as PokemonListEvent.OnSuccess).data,
                            onPokeminItemClicked = onPokeminItemClicked,
                        )
                    }

                    else -> Unit
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    modifier: Modifier = Modifier,
    isEnabled: (Boolean) = true,
    onSearchClicked: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
) {
    var isTextFieldFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.dp16,
                end = Dimens.dp16,
                top = Dimens.dp16,
                bottom = Dimens.dp16,
            )
            .clickable { onSearchClicked() },
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            enabled = isEnabled,
            modifier = modifier
                .focusRequester(FocusRequester())
                .onFocusChanged { isTextFieldFocused = it.isFocused }
                .fillMaxWidth()
                .heightIn(min = Dimens.dp48, max = Dimens.dp48)
                .clip(shape = RoundedCornerShape(Dimens.dp8))
                .border(
                    border = BorderStroke(
                        Dimens.dp1,
                        Color.Gray,
                    ),
                    shape = RoundedCornerShape(Dimens.dp8),
                ),
            textStyle = TextStyle(fontSize = Dimens.sp14),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.dp20),
                )
            },
            trailingIcon = {
                if (isTextFieldFocused && query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.dp20),
                        )
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
            ),
            placeholder = {
                Text(
                    text = "Cari disini...",
                    fontSize = 14.sp,
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(onSearch = {}),
        )
    }
}

@Composable
private fun ShowLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        LoadingWheel("Loading....")
    }
}

@Composable
private fun ShowPokemonList(
    modifier: Modifier,
    data: List<PokemonResponseModel>,
    onPokeminItemClicked: (PokemonResponseModel) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        items(data) { pokemon ->
            PokemonItem(
                modifier = modifier,
                pokemon = pokemon,
                onPokeminItemClicked = onPokeminItemClicked,
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
            .clickable(
                enabled = true,
                onClick = {
                    onPokeminItemClicked.invoke(pokemon)
                },
            ),
    ) {
        Column(modifier = modifier.align(Alignment.CenterHorizontally)) {
            GlideImage(
                model = pokemon.url.getImageUrl(),
                contentDescription = pokemon.name,
                modifier.align(Alignment.CenterHorizontally),
                loading = placeholder(R.drawable.pokemon_ball)
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

@Preview(showBackground = true)
@Composable
private fun MyPokemonPreview() {
    PokemonAppTheme {
        PokemonListScreen()
    }
}

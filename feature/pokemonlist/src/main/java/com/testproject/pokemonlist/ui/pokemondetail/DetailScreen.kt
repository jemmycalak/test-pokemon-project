package com.testproject.pokemonlist.ui.pokemondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.testproject.core.templete.MainTemplate
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.model.Pokemon
import com.testproject.pokemonlist.R
import com.testproject.pokemonlist.ui.renamepokemon.ShowRenameDialog

@Composable
fun DetailPokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailPokemonViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {},
    showSnackBar: (String, String?) -> Unit,
) {
    LaunchedEffect(key1 = viewModel.pokemon) {
        viewModel.getDetailPokemon()
    }
    val pokemonData by viewModel.event.collectAsState(initial = null)
    val context = LocalContext.current
    LaunchedEffect(key1 = pokemonData) {
        when (pokemonData) {
            is DetailPokemonEvent.OnSuccessCatchPokemon -> showSnackBar(
                context.getString(R.string.success_catch_pokemon),
                null
            )

            DetailPokemonEvent.OnSuccessReleasePokemon -> {
                showSnackBar(
                    context.getString(R.string.success_release),
                    null
                )
                navigateBack()
            }

            else -> Unit
        }
    }
    var isShowRenameDialog by remember {
        mutableStateOf<Pair<Boolean, Pokemon?>>(
            Pair(
                false,
                null
            )
        )
    }
    val onCatchPokemon: ((Pokemon) -> Unit?)? = if (!viewModel.isCatched) {
        { viewModel.cathPokemon() }
    } else null
    val onShowRenameDialog: ((Pokemon) -> Unit?)? = if (viewModel.isCatched) {
        { pokemon ->
            isShowRenameDialog = Pair(true, pokemon)
            null
        }
    } else null
    val onRenamePokemon: ((Pokemon) -> Unit?)? = if (viewModel.isCatched) {
        { pokemon -> viewModel.renamePokemon(pokemon.name.orEmpty()) }
    } else null
    val onReleasePokemon: ((Pokemon) -> Unit?)? = if (viewModel.isCatched) {
        {
            viewModel.releasePokemon()
        }
    } else null
    val onRenameDialogDismiss = {
        isShowRenameDialog = Pair(false, null)
    }

    DetailPokemon(
        modifier,
        pokemonData,
        navigateBack,
        isShowRenameDialog,
        onCatchPokemon = onCatchPokemon,
        onRenamePokemon = onRenamePokemon,
        onReleasePokemon = onReleasePokemon,
        onRenameDialogDismiss = onRenameDialogDismiss,
        onShowRenameDialog = onShowRenameDialog,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailPokemon(
    modifier: Modifier = Modifier,
    pokemonData: DetailPokemonEvent?,
    navigateBack: () -> Unit = {},
    isShowRenameDialog: Pair<Boolean, Pokemon?>? = null,
    onCatchPokemon: ((Pokemon) -> Unit?)? = null,
    onRenamePokemon: ((Pokemon) -> Unit?)? = null,
    onReleasePokemon: ((Pokemon) -> Unit?)? = null,
    onRenameDialogDismiss: () -> Unit = {},
    onShowRenameDialog: ((Pokemon) -> Unit?)? = null,
) {
    MainTemplate(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = modifier,
                        text = stringResource(R.string.title_detailpokemon),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navigateBack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "Back Icon")
                    }
                },
            )
        },
        content = {
            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {
                when (pokemonData) {
                    DetailPokemonEvent.OnNetworkError -> ShowError()
                    is DetailPokemonEvent.OnShowDetailPokemon -> {
                        ShowPokemon(
                            modifier = modifier,
                            pokemon = pokemonData.data,
                            onCatchPokemon = onCatchPokemon,
                            onRenamePokemon = onShowRenameDialog,
                            onReleasePokemon = onReleasePokemon,
                        )
                    }

                    DetailPokemonEvent.OnShowLoading -> ShowLoading()
                    is DetailPokemonEvent.OnSuccessCatchPokemon -> {
                        ShowPokemon(
                            modifier = modifier,
                            pokemon = pokemonData.data,
                            onCatchPokemon = null,
                            onRenamePokemon = onRenamePokemon,
                            onReleasePokemon = onReleasePokemon,
                        )
                    }

                    is DetailPokemonEvent.OnSuccessRenamePokemon -> {
                        ShowPokemon(
                            modifier = modifier,
                            pokemon = pokemonData.data,
                            onCatchPokemon = null,
                            onRenamePokemon = onRenamePokemon,
                            onReleasePokemon = onReleasePokemon,
                        )
                    }

                    else -> Unit
                }
            }
            ShowRenameDialog(
                isShowBottomSheet = isShowRenameDialog?.first ?: false,
                pokemon = isShowRenameDialog?.second,
                onRenamePokemon = onRenamePokemon,
                onDismissDialog = onRenameDialogDismiss
            )
        },
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ShowPokemon(
    modifier: Modifier = Modifier,
    pokemon: Pokemon?,
    onCatchPokemon: ((Pokemon) -> Unit?)? = null,
    onRenamePokemon: ((Pokemon) -> Unit?)? = null,
    onReleasePokemon: ((Pokemon) -> Unit?)? = null,
) {
    pokemon?.let { pokemon ->
        Box(
            modifier = modifier
                .fillMaxSize(),
        ) {
            Image(
                modifier = modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )

            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = modifier.padding(top = 10.dp))
                GlideImage(
                    model = pokemon.sprites?.other?.home?.frontDefault,
                    contentDescription = pokemon.name,
                    modifier = modifier
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape),
                )
                Spacer(modifier = modifier.padding(top = 20.dp))
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = modifier.weight(1f),
                        textAlign = TextAlign.End,
                        text = "Name"
                    )
                    Text(
                        modifier = modifier.weight(1f),
                        text = ": ${pokemon.name}".uppercase(),
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = modifier.weight(1f),
                        textAlign = TextAlign.End,
                        text = "Type"
                    )
                    Text(
                        modifier = modifier.weight(1f),
                        text = ": ${pokemon.types[0].type?.name}".uppercase()
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "HP",
                        modifier = modifier.weight(1f),
                        textAlign = TextAlign.End,
                    )
                    Text(
                        text = ": ${pokemon.stats[0].baseStat}",
                        modifier = modifier.weight(1f),
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Attack",
                        modifier = modifier.weight(1f),
                        textAlign = TextAlign.End,
                    )
                    Text(
                        text = ": ${pokemon.stats[1].baseStat}",
                        modifier = modifier.weight(1f),
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Defense",
                        modifier = modifier.weight(1f),
                        textAlign = TextAlign.End,
                    )
                    Text(
                        text = ": ${pokemon.stats[2].baseStat}",
                        modifier = modifier.weight(1f),
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Special Attack",
                        modifier = modifier.weight(1f),
                        textAlign = TextAlign.End,
                    )
                    Text(
                        text = ": ${pokemon.stats[3].baseStat}",
                        modifier = modifier.weight(1f),
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Special Defense",
                        modifier = modifier.weight(1f),
                        textAlign = TextAlign.End,
                    )
                    Text(
                        text = ": ${pokemon.stats[4].baseStat}",
                        modifier = modifier.weight(1f),
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Speed",
                        modifier = modifier.weight(1f),
                        textAlign = TextAlign.End,
                    )
                    Text(
                        text = ": ${pokemon.stats[5].baseStat}",
                        modifier = modifier.weight(1f),
                    )
                }
                ShowButton(
                    modifier = modifier,
                    title = stringResource(id = R.string.catch_pokemon),
                    pokemon = pokemon,
                    onClick = onCatchPokemon
                )
                ShowButton(
                    modifier = modifier,
                    title = stringResource(id = R.string.rename_pokemon),
                    pokemon = pokemon,
                    onClick = onRenamePokemon
                )
                ShowButton(
                    modifier = modifier,
                    title = stringResource(id = R.string.release_pokemon),
                    pokemon = pokemon,
                    onClick = onReleasePokemon
                )
            }
        }
    }
}

@Composable
private fun ShowButton(
    modifier: Modifier = Modifier,
    title: String,
    pokemon: Pokemon,
    onClick: ((Pokemon) -> Unit?)? = null,
) {
    onClick?.let {
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onClick = { onClick(pokemon) },
        ) {
            Text(text = title)
        }
    }
}

@Composable
private fun ShowLoading() {
}

@Composable
private fun ShowError() {
}

@Preview(showBackground = true)
@Composable
private fun MyPokemonPreview() {
    PokemonAppTheme {
        DetailPokemon(
            pokemonData = DetailPokemonEvent.OnShowLoading,
        )
    }
}

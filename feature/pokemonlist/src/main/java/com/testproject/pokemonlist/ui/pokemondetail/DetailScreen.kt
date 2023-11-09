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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

/*
@AndroidEntryPoint
class DetailFragment : Fragment(), InputNameDialogFragment.Listener {

    private val args: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailPokemonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setObserver()
        viewModel.getDetailPokemon(args.pokemon.url.getIdFromUrl())
    }

    private fun initUI() {
        with(binding) {
            val visibleButtonCatch = if (args.isCatched) View.GONE else View.VISIBLE
            val visibleButtonRelease = if (args.isCatched) View.VISIBLE else View.GONE
            cathPokemon.apply {
                setOnClickListener {
                    viewModel.cathPokemon(args.pokemon)
                    visibility = visibleButtonRelease
                }
                visibility = visibleButtonCatch
            }
            renamePokemon.apply {
                visibility = visibleButtonRelease
                setOnClickListener {
                    InputNameDialogFragment.create(args.pokemon.name).apply {
                        setListener(this@DetailFragment)
                    }.show(childFragmentManager, this@DetailFragment::class.java.name)
                }
            }
            releasePokemon.apply {
                visibility = visibleButtonRelease
                setOnClickListener {
                    viewModel.releasePokemon(args.pokemon)
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.loadPokemon.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    setupViews(it.data)
                }

                is Resource.Failure -> Snackbar.make(
                    requireView(),
                    "Error get detail pokemon",
                    Snackbar.LENGTH_LONG,
                ).show()
            }
        }
        viewModel.event.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    is DetailPokemonEvent.OnSuccessInsertPokemon -> {
                        requireView().showSnackbar("Sukses Menangkap Pokemon")
                    }

                    is DetailPokemonEvent.OnSuccessReleasePokemon -> {
                        requireView().showSnackbar("Sukses Release Pokemon")
                    }

                    is DetailPokemonEvent.OnSuccessRenamePokemon -> {
                        requireView().showSnackbar("Sukses Rename Pokemon")
                        binding.pokemonName.text = event.name
                    }
                }
            }
        }
    }

    private fun setupViews(pokemon: Pokemon) {
        with(binding) {
            Glide.with(requireContext()).load(pokemon.sprites?.other?.home?.frontDefault)
                .into(pokemonImage)
            pokemonType1.text = pokemon.types[0].type?.name
            hp.text = pokemon.stats[0].baseStat.toString()
            attack.text = pokemon.stats[1].baseStat.toString()
            defense.text = pokemon.stats[2].baseStat.toString()
            specialAttack.text = pokemon.stats[3].baseStat.toString()
            specialDefense.text = pokemon.stats[4].baseStat.toString()
            speed.text = pokemon.stats[5].baseStat.toString()

            val name = if (args.isCatched) args.pokemon.name else pokemon.name
            pokemonName.text = name
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSaveName(name: String) {
        viewModel.renamePokemon(
            args.pokemon.copy(
                name = name,
            ),
        )
    }
}*/

@Composable
fun DetailPokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailPokemonViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {},
) {
    LaunchedEffect(key1 = viewModel.pokemon) {
        viewModel.getDetailPokemon()
    }
    val pokemonData by viewModel.event.collectAsState(initial = null)

    DetailPokemon(
        modifier,
        pokemonData,
        navigateBack,
        onCatchPokemon = {
            viewModel.cathPokemon()
        },
        onRenamePokemon = {
            viewModel.renamePokemon()
        },
        onReleasePokemon = {
            viewModel.releasePokemon()
            navigateBack()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun DetailPokemon(
    modifier: Modifier = Modifier,
    pokemonData: DetailPokemonEvent?,
    navigateBack: () -> Unit = {},
    onCatchPokemon: (Pokemon) -> Unit = {},
    onRenamePokemon: (Pokemon) -> Unit = {},
    onReleasePokemon: (Pokemon) -> Unit = {},
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
                            onRenamePokemon = onRenamePokemon,
                            onReleasePokemon = onReleasePokemon,
                        )
                    }

                    DetailPokemonEvent.OnShowLoading -> ShowLoading()
                    DetailPokemonEvent.OnSuccessInsertPokemon -> Unit
                    DetailPokemonEvent.OnSuccessReleasePokemon -> Unit
                    is DetailPokemonEvent.OnSuccessRenamePokemon -> Unit
                    null -> Unit
                }
            }
        },
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ShowPokemon(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onCatchPokemon: (Pokemon) -> Unit = {},
    onRenamePokemon: (Pokemon) -> Unit = {},
    onReleasePokemon: (Pokemon) -> Unit = {},
) {
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

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onClick = { onCatchPokemon(pokemon) },
            ) {
                Text(text = "TANGKAP")
            }
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onClick = { onRenamePokemon(pokemon) },
            ) {
                Text(text = "RENAME")
            }
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onClick = { onReleasePokemon(pokemon) },
            ) {
                Text(text = "RElEASE")
            }
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
        DetailPokemon(pokemonData = DetailPokemonEvent.OnShowLoading)
    }
}

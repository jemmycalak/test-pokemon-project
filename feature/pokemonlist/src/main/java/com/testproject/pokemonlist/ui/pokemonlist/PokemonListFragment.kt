package com.testproject.pokemonlist.ui.pokemonlist

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.testproject.core.component.LoadingWheel
import com.testproject.core.getIdFromUrl
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.model.PokemonListResponseModel
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonapp.core.Resource

/*@AndroidEntryPoint
class PokemonListFragment : Fragment(), PokemonListener {

    private val viewModel: PokemonListViewModel by viewModels()
    private lateinit var binding: FragmentPokemonListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentPokemonListBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        setObserver()
        viewModel.getPokemonList(1, 100)
    }

    private fun setUI() {
        with(binding) {
            recyclerView.adapter = PokemonAdapter(this@PokemonListFragment)
        }
    }

    private fun setObserver() {
        viewModel.listPokemon.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as PokemonAdapter).submitList(it)
        }
        viewModel.event.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                requireView().showSnackbar("Gagal memuat data")
            }
        }
    }

    override fun onPokemonClicked(data: PokemonResponseModel) {
        findNavController().navigate(
            PokemonListFragmentDirections.actionPokemonListFragmentToDetailFragment(
                data,
            ),
        )
    }
}*/

@Composable
internal fun PokemonList(
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel(),
    onPokeminItemClicked: (Int) -> Unit = {},
    showActionBar: (String, String?) -> Unit = { _, _ -> },
) {
    val pokemonData by viewModel.pokemonData.collectAsState(initial = Resource.Loading())

    Surface {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (pokemonData) {
                is Resource.Loading -> {
                    ShowLoading(modifier)
                }

                is Resource.Failure -> {
                    showActionBar("Failed load data", null)
                }

                is Resource.Success -> {
                    ShowPokemonList(
                        modifier = modifier,
                        data = (pokemonData as Resource.Success<PokemonListResponseModel>).data.results,
                        onPokeminItemClicked = onPokeminItemClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun ShowLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxHeight().fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        LoadingWheel("Loading....")
    }
}

@Composable
private fun ShowPokemonList(
    modifier: Modifier,
    data: List<PokemonResponseModel>,
    onPokeminItemClicked: (Int) -> Unit,
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
    onPokeminItemClicked: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable(enabled = true, onClick = { onPokeminItemClicked.invoke(pokemon.id) }),
    ) {
        Column(modifier = modifier.align(Alignment.CenterHorizontally)) {
            GlideImage(
                model = getImageUrl(pokemon.url),
                contentDescription = pokemon.name,
                modifier.align(Alignment.CenterHorizontally),
            )
            Text(
                text = pokemon.name,
                modifier.align(Alignment.CenterHorizontally).padding(bottom = 4.dp),
            )
        }
    }
}

private fun getImageUrl(url: String): String {
    val IMAGE_HOST_URL =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
    return try {
        val id = url.getIdFromUrl()
        "$IMAGE_HOST_URL/$id.png"
    } catch (e: Exception) {
        ""
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPokemonPreview() {
    PokemonAppTheme {
        PokemonList()
    }
}

package com.testproject.pokemonlist.ui.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonlist.ui.pokemonlist.adapter.PokemonAdapter
import com.testproject.pokemonlist.ui.pokemonlist.adapter.PokemonListener
import com.testproject.pokemonapp.utils.showSnackbar
import com.testproject.pokemonlist.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
}


@Composable
fun PokemonList(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "List Pokemnon")
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPokemonPreview() {
    PokemonAppTheme {
        PokemonList()
    }
}

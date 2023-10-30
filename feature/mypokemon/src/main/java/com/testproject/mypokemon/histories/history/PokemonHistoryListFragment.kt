package com.testproject.mypokemon.histories.history

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
import com.testproject.mypokemon.databinding.FragmentPokemonHistoryBinding
import com.testproject.mypokemon.histories.history.adapter.PokemonAdapter
import com.testproject.mypokemon.histories.history.adapter.PokemonListener
import com.testproject.pokemonapp.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonHistoryListFragment : Fragment(), PokemonListener {

    private val viewModel: PokemonHistoryListViewModel by viewModels()
    private lateinit var binding: FragmentPokemonHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentPokemonHistoryBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        setObserver()
        viewModel.getPokemonList()

        /*val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://example.google.app/settings_fragment_two".toUri())
            .build()
        findNavController().navigate(request)*/
    }

    private fun setUI() {
        with(binding) {
            recyclerView.adapter = PokemonAdapter(this@PokemonHistoryListFragment)
        }
    }

    private fun setObserver() {
        viewModel.listPokemon.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as PokemonAdapter).submitList(it)
        }
        viewModel.event.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                requireView().showSnackbar("Pokemon kamu masih kosong")
            }
        }
    }

    override fun onPokemonClicked(data: PokemonResponseModel) {
        findNavController().navigate(
            PokemonHistoryListFragmentDirections.actionPokemonHistoryListFragmentToDetailFragment(
                data,
            ),
        )
    }
}

@Composable
fun MyPokemon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "My Pokemnon")
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPokemonPreview() {
    PokemonAppTheme {
        MyPokemon()
    }
}

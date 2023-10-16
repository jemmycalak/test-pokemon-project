package com.testproject.mypokemon.histories.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.testproject.model.PokemonResponseModel
import com.testproject.mypokemon.databinding.FragmentPokemonHistoryBinding
import com.testproject.mypokemon.histories.history.adapter.PokemonAdapter
import com.testproject.pokemonapp.utils.showSnackbar
import com.testproject.mypokemon.histories.history.adapter.PokemonListener
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
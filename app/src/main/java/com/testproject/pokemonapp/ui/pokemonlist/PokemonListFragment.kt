package com.testproject.pokemonapp.ui.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.testproject.pokemonapp.data.model.PokemonResponseModel
import com.testproject.pokemonapp.databinding.FragmentPokemonListBinding
import com.testproject.pokemonapp.ui.pokemonlist.adapter.PokemonAdapter
import com.testproject.pokemonapp.ui.pokemonlist.adapter.PokemonListener
import com.testproject.pokemonapp.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : Fragment(), PokemonListener {

    companion object {
        fun newInstance() = PokemonListFragment()
    }

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

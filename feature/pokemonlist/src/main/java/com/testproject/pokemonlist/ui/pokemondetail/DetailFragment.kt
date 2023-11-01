package com.testproject.pokemonlist.ui.pokemondetail

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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.testproject.core.getIdFromUrl
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.model.Pokemon
import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonapp.utils.showSnackbar
import com.testproject.pokemonlist.databinding.FragmentDetailBinding
import com.testproject.pokemonlist.ui.renamepokemon.InputNameDialogFragment
import dagger.hilt.android.AndroidEntryPoint

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
}

@Composable
fun DetailPokemon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Detail Pokemnon")
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPokemonPreview() {
    PokemonAppTheme {
        DetailPokemon()
    }
}

package com.testproject.pokemonlist.ui.renamepokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.testproject.pokemonlist.databinding.FragmentInputNameDialogBinding

class InputNameDialogFragment : DialogFragment() {

    interface Listener {
        fun onSaveName(name: String)
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    private lateinit var binding: FragmentInputNameDialogBinding
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentInputNameDialogBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        initUi()
        initData()
    }

    private fun initData() {
        val name = arguments?.getString(NAME, "")
        binding.pokemonName.setText(name)
    }

    private fun initUi() {
        with(binding) {
            savePokemon.apply {
                setOnClickListener {
                    listener?.onSaveName(pokemonName.text.toString())
                    dismiss()
                }
            }
            cancelSave.apply {
                setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    companion object {
        const val NAME = "name"
        fun create(name: String): InputNameDialogFragment {
            return InputNameDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME, name)
                }
            }
        }
    }
}

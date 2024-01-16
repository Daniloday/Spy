package com.missclick.spy.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.MaterialFadeThrough
import com.missclick.spy.MainActivity
import com.missclick.spy.R
import com.missclick.spy.data.local.SettingsRepository
import com.missclick.spy.databinding.FragmentSettingsBinding
import com.missclick.spy.ui.menu.MenuViewModel
import com.missclick.spy.utills.LocalLanguage
import com.missclick.spy.utills.SetsManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel : SettingsViewModel by viewModel()
    private val setsManager : SetsManager by inject()
    private var firstOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.language.asLiveData().observe(viewLifecycleOwner){
            if (!firstOpen){
                Toast.makeText(requireContext(), requireContext().getString(R.string.changed_lang), Toast.LENGTH_SHORT ).show()
            }else{
                firstOpen = false
            }
            updateColor(LocalLanguage.mapStringToLang(it))
        }

        viewModel.initManager.observe(viewLifecycleOwner){
            if(it){
                setsManager.initSets(resources)
                viewModel.preloadDb(setsManager.getWords())
            }
        }


        binding.cardViewEng.setOnClickListener {
            LocalLanguage.changeLocale(resources, LocalLanguage.English)
            viewModel.setLanguage(LocalLanguage.English, getString(R.string.basic))
        }

        binding.cardViewRus.setOnClickListener {
            LocalLanguage.changeLocale(resources, LocalLanguage.Russian)
            viewModel.setLanguage(LocalLanguage.Russian, getString(R.string.basic))
        }

        binding.cardViewUkr.setOnClickListener {
            LocalLanguage.changeLocale(resources, LocalLanguage.Ukrainian)
            viewModel.setLanguage(LocalLanguage.Ukrainian, getString(R.string.basic))
        }

        binding.appCompatImageButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.appCompatImageButton1.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun updateColor(lang : LocalLanguage){
        val white = R.color.white
        binding.textRus.setTextColor(resources.getColor(white))
        binding.textEng.setTextColor(resources.getColor(white))
        binding.textUkr.setTextColor(resources.getColor(white))
        val orange = R.color.orange
        when(lang){
            is LocalLanguage.English -> binding.textEng.setTextColor(resources.getColor(orange))
            is LocalLanguage.Russian -> binding.textRus.setTextColor(resources.getColor(orange))
            is LocalLanguage.Ukrainian -> binding.textUkr.setTextColor(resources.getColor(orange))
        }

    }

}
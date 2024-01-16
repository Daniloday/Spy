package com.missclick.spy.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.missclick.spy.R
import com.missclick.spy.data.models.WordsModel
import com.missclick.spy.databinding.FragmentSplashBinding
import com.missclick.spy.utills.LocalLanguage
import com.missclick.spy.utills.SetsManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash){
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel : SplashViewModel by viewModel()
    private val setsManager : SetsManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.language.asLiveData().observe(viewLifecycleOwner){ lang ->
            LocalLanguage.changeLocale(resources, LocalLanguage.mapStringToLang(lang))
            setsManager.initSets(resources)
            Log.e("Splash", setsManager.getKeys().toString())
            viewModel.getFirstLaunch().observe(viewLifecycleOwner){
                if(it)
                    viewModel.preloadDb(setsManager.getWords())
                else {
                    Log.e("splash", "go")
                    findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                }
            }
            viewModel.ids.observe(viewLifecycleOwner){
                viewModel.setFirstLaunch(false)
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }
    }

//    private fun getWordsFromStringArray() : List<WordsModel>{
//        val wordsBasic = resources.getStringArray(R.array.basic)
//        val wordsCountries = resources.getStringArray(R.array.countries)
//        val wordsTransport = resources.getStringArray(R.array.transport)
//        val wordsObjects = resources.getStringArray(R.array.objects)
//        return (wordsBasic.map {
//            WordsModel(word = it, category = getString(R.string.basic))
//        }+
//        wordsCountries.map {
//            WordsModel(word = it, category = getString(R.string.countries))
//        }+
//         wordsTransport.map {
//            WordsModel(word = it, category = getString(R.string.transport))
//        }+
//                wordsObjects.map {
//            WordsModel(word = it, category = getString(R.string.objects))
//        })
//
//    }
}
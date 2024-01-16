package com.missclick.spy.ui.sets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.MaterialContainerTransform
import com.missclick.spy.R
import com.missclick.spy.adapters.CollectionsListAdapter
import com.missclick.spy.data.models.CollectionsModel
import com.missclick.spy.databinding.FragmentSetsBinding
import com.missclick.spy.ui.words.WordsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetsFragment : Fragment(R.layout.fragment_sets) {
    private val binding by viewBinding(FragmentSetsBinding::bind)
    private val viewModel : SetsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSets().observe(viewLifecycleOwner){ it ->
            //val mutableItems = mutableListOf<String>().apply { addAll(it) }
            val collections = it.map {
                CollectionsModel(name = it)
            }.toMutableList()
            binding.recycleSets.adapter = CollectionsListAdapter(collections){
                findNavController().navigate(R.id.action_setsFragment_to_wordsFragment, WordsFragment.newInstance(setName = it.name, newSet = false))
            }
            binding.recycleSets.layoutManager = LinearLayoutManager(requireActivity())
        }
        binding.appCompatImageButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.appCompatImageButton1.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.cardAdd.setOnClickListener {
            findNavController().navigate(R.id.action_setsFragment_to_wordsFragment,WordsFragment.newInstance(setName = "", newSet = true))
        }
    }
}
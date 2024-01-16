package com.missclick.spy.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import com.missclick.spy.R
import com.missclick.spy.data.models.GameParams
import com.missclick.spy.databinding.FragmentMenuBinding
import com.missclick.spy.ui.cards.CardsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {
    private val viewModel : MenuViewModel by viewModel()
    private val binding by viewBinding(FragmentMenuBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        exitTransition = MaterialFadeThrough()

       // exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
    }


    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.adView.loadAd(AdRequest.Builder().build())

        var players = 4
        var spies = 1
        var time = 3
        var set = getString(R.string.basic)
        //todo default from dataStore
        viewModel.players.asLiveData().observe(viewLifecycleOwner){
            players = it
            updatePlayers(players)
            updateSpies(spies,players)
        }
        viewModel.spy.asLiveData().observe(viewLifecycleOwner){
            spies = it
            updateSpies(spies,players)
        }
        viewModel.time.asLiveData().observe(viewLifecycleOwner){
            time = it
            updateTime(time)
        }
        viewModel.set.asLiveData().observe(viewLifecycleOwner){
            set = it
            if(set == "basic")
                set = getString(R.string.basic)
            updateSet(set)
        }

        binding.imagePlayersLeft.setOnClickListener {
            if (players > 2){
                players -= 1
                if (players == spies)
                    spies -= 1
                updatePlayers(players)
                updateSpies(spies,players)
            }
        }
        binding.imagePlayersRight.setOnClickListener {
            if (players < 20){
                players += 1
                updatePlayers(players)
                updateSpies(spies,players)
            }
        }

        binding.imageSpiesLeft.setOnClickListener {
            if (spies > 1){
                spies -= 1
                updateSpies(spies,players)
            }
        }
        binding.imageSpiesRight.setOnClickListener {
            if (spies < players - 1){
                spies += 1
                updateSpies(spies,players)
            }
        }

        binding.imageTimeLeft.setOnClickListener {
            if (time > 1){
                time -= 1
                updateTime(time)
            }
        }
        binding.imageTimeRight.setOnClickListener {
            if (time < 9){
                time += 1
                updateTime(time)
            }
        }


        binding.buttonStart.setOnClickListener {
            val param = GameParams(
                    players = players,
                    spy = spies,
                    time = time,
                    category = set
            )
            viewModel.setGameParams(param)
            findNavController().navigate(R.id.action_menuFragment_to_cardsFragment, CardsFragment.newInstance(params = param))
        }
        binding.imageHelp.setOnClickListener {
            findNavController().navigate(R.id.action_cardsFragment_to_helpFragment)
        }
        binding.imageSettings.setOnClickListener {
            findNavController().navigate(R.id.action_cardsFragment_to_settingsFragment)
        }
        binding.imageSets.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_setsFragment)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updatePlayers(players : Int){
        if (players == 2)
            binding.imagePlayersLeft.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_left_2))
        else
            binding.imagePlayersLeft.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_left))
        if (players == 20)
            binding.imagePlayersRight.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_right_2))
        else
            binding.imagePlayersRight.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_right))
        binding.textPlayers.text = players.toString()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateSpies(spies : Int, players: Int){
        if (spies == 1)
            binding.imageSpiesLeft.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_left_2))
        else
            binding.imageSpiesLeft.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_left))
        if (players == spies + 1)
            binding.imageSpiesRight.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_right_2))
        else
            binding.imageSpiesRight.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_right))
        binding.textSpy.text = spies.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun updateTime(time : Int){
        if (time == 1)
            //binding.imageTimeLeft.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_left_2))
            binding.imageTimeLeft.setImageResource(R.drawable.ic_orange_left_2) //todo sdelat tak
        else
            binding.imageTimeLeft.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_left))
        if (time == 9)
            binding.imageTimeRight.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_right_2))
        else
            binding.imageTimeRight.setImageDrawable(resources.getDrawable(R.drawable.ic_orange_right))
        binding.textTimer.text = time.toString() + " " + getString(R.string.min)
    }
    private fun updateSet(setName : String){
        binding.textSet.text = setName
    }


}
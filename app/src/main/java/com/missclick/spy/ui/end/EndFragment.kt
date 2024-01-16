package com.missclick.spy.ui.end

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.missclick.spy.MainActivity
import com.missclick.spy.R
import com.missclick.spy.databinding.FragmentEndBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val PARAMS_ARG = "spy"
class EndFragment : Fragment(R.layout.fragment_end) {
    private val binding by viewBinding(FragmentEndBinding::bind)

    var spies : ArrayList<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            spies = it.getIntegerArrayList(PARAMS_ARG)
        }

//        (activity as MainActivity).mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
//            override fun onAdClicked() {
//                println("1")
//            }
//
//            override fun onAdDismissedFullScreenContent() {
//                println("2")
//                (activity as MainActivity).mInterstitialAd = null
//                findNavController().navigate(R.id.action_endFragment_to_menuFragment)
//
//            }
//
//            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
//                println("3")
//                (activity as MainActivity).mInterstitialAd = null
//                findNavController().navigate(R.id.action_endFragment_to_menuFragment)
//            }
//
//            override fun onAdImpression() {
//                println("4")
//
//            }
//
//            override fun onAdShowedFullScreenContent() {
//                println("5")
//
//            }
//        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (spies!!.size == 1)
            binding.textPlayer.text = "${binding.textPlayer.text} ${spies!![0]}"
        else{
            var spy = ""
            for (i in spies!!){
                spy += "$i,"
            }
            spy = spy.dropLast(1)
            binding.textWasSpy.text = resources.getString(R.string.were_spies)
            binding.textPlayer.text = "${resources.getString(R.string.players)} $spy"
        }

        binding.buttonRestart.setOnClickListener {
            (activity as MainActivity).mInterstitialAd?.show(requireActivity())
            (activity as MainActivity).mInterstitialAd = null
            findNavController().navigate(R.id.action_endFragment_to_menuFragment)


        }
    }

    companion object{
        fun newInstance(spy: ArrayList<Int>) =
                Bundle().apply {
                    putIntegerArrayList(PARAMS_ARG, spy)
                }
    }
}
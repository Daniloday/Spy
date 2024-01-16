package com.missclick.spy.ui.timer

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.missclick.spy.MainActivity
import com.missclick.spy.R
import com.missclick.spy.data.models.GameParams
import com.missclick.spy.databinding.FragmentTimerBinding
import com.missclick.spy.ui.cards.CardsFragment
import com.missclick.spy.ui.end.EndFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Error
import java.lang.Exception

private const val PARAMS_ARG_TIME = "time"
private const val PARAMS_ARG_SPY = "spy"

class TimerFragment : Fragment(R.layout.fragment_timer){

    private val binding by viewBinding(FragmentTimerBinding::bind)

    var timer : CountDownTimer? = null

    private var time : Int? = null
    private var spy : ArrayList<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            (activity as MainActivity).loadInterstitialAdd()
        }

        arguments?.let {
            time = it.getInt(PARAMS_ARG_TIME)
            spy = it.getIntegerArrayList(PARAMS_ARG_SPY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer = object : CountDownTimer(time?.toLong()!!, 1000){
            override fun onFinish() {
                //todo string to values
                binding.timerText.text = "Time end"

            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val sec = millisUntilFinished % 60000 / 1000
                val t =
                    if(sec < 10) "${millisUntilFinished / 1000 / 60}:0${sec}"
                    else "${millisUntilFinished / 1000 / 60}:${sec}"
                binding.timerText.text = t
            }
        }
        (timer as CountDownTimer).start()
        binding.buttonShowSpy.setOnClickListener {
            spy?.let {
                it1 -> navigate(it1)
            }
        }
    }

    private fun navigate(spies : ArrayList<Int>){
        timer?.cancel()
        findNavController().navigate(R.id.action_timerFragment_to_endFragment, EndFragment.newInstance(spy = spies))
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    companion object{
        fun newInstance(time: Int, spy : ArrayList<Int>) =
            Bundle().apply {
                putInt(PARAMS_ARG_TIME, time)
                putIntegerArrayList(PARAMS_ARG_SPY, spy)
            }
    }
}


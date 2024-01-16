package com.missclick.spy.ui.cards

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.MaterialSharedAxis
import com.missclick.spy.R
import com.missclick.spy.data.models.GameParams
import com.missclick.spy.databinding.FragmentCardsBinding
import com.missclick.spy.ui.timer.TimerFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val PARAMS_ARG = "params"

class CardsFragment : Fragment(R.layout.fragment_cards) {
    private val viewModel : CardsViewModel by viewModel()
    private val binding by viewBinding(FragmentCardsBinding::bind)


    private var params : GameParams? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        arguments?.let {
            params = it.getSerializable(PARAMS_ARG) as GameParams
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getRandomWord(params!!.category).observe(viewLifecycleOwner){
//            val spy = viewModel.getSpy(params!!.players)
//
//        }
        Log.e("params",params.toString())
        binding.appCompatImageButton.setOnClickListener {
            findNavController().navigateUp()
        }

        if (params != null){
            viewModel.getRandomWord(params!!.category).observe(viewLifecycleOwner){ role ->
                //Log.e("Role", role)
                //val spy = viewModel.getSpy(params!!.players)
                val spies = viewModel.getSpies(params!!.players, params!!.spy)
                //Log.e("Spy", spy.toString())
                viewModel.cardState.observe(viewLifecycleOwner){
                    //Log.e("Kek", "CardState")
                    when(it){
                        is CardState.ClosedCard ->
                            binding.apply {
                                roleImageBottom.visibility = View.GONE
                                nameRoleBottom.text = getString(R.string.player) + " " + it.number.toString()
                                descriptionRoleBottom.text = getString(R.string.click_to_see_you_role)
                            }
                        is CardState.OpenedCard -> {
                            //Log.e("Number", it.number.toString())
                            if (it.number in spies)
                                binding.apply {
                                    println("here")
                                    roleImageBottom.setImageResource(R.drawable.ic_spy_hat)
                                    //old
                                    nameRoleBottom.text = getString(R.string.you_spy)
                                    //new hard mode
//                                    nameRoleBottom.text = role[1]
                                    descriptionRoleBottom.text = getString(R.string.you_spy_hint)
                                }
                            else
                                binding.apply {
                                    roleImageBottom.setImageResource(R.drawable.ic_member_location)
                                    //old
                                    nameRoleBottom.text = role
                                    //new hard mode
//                                    nameRoleBottom.text = role[0]
                                    descriptionRoleBottom.text = getString(R.string.you_member)
                                }
                            binding.roleImageBottom.visibility = View.VISIBLE

                        }
                        is CardState.EndCard -> {
                            findNavController().navigate(
                                    R.id.action_cardsFragment_to_timerFragment,
                                    TimerFragment.newInstance(params!!.time * 1000 * 60, ArrayList(spies)))
                        }
                    }
                }
            }
            binding.cardView.setOnClickListener{
                binding.motionLayout.transitionToState(R.id.like)
                Log.e("Click", "C")
                viewModel.changeState(params!!.players)
            }
        }
        binding.motionLayout.setTransitionListener(object : TransitionAdapter(){
//            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
//                when(currentId){
//                    R.id.offScreenLike -> {
//                        motionLayout!!.progress = 0f
////                        binding.roleImage.setImageDrawable(binding.roleImageBottom.drawable)
////                        binding.nameRole.text = binding.nameRoleBottom.text
////                        binding.descriptionRole.text = binding.descriptionRoleBottom.text
//                        //motionLayout.transitionToState(R.id.rest)
//                        motionLayout!!.setTransition(R.id.rest, R.id.like)
//                    }
//                }
//            }
        })
    }


    companion object{
        fun newInstance(params: GameParams) =
                Bundle().apply {
                        putSerializable(PARAMS_ARG, params)
                    }

    }
}
package com.missclick.spy.ui.viewpager

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.missclick.spy.R
import com.missclick.spy.databinding.FragmentGuideBinding

private const val ARG_PARAM1 = "param1"

class GuideFragment : Fragment(R.layout.fragment_guide){
    private val binding by viewBinding(FragmentGuideBinding::bind)

    private var param1: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.tv.text = param1.toString()
        when(param1){
            0 -> binding.apply {
                shapeableImageView.setImageResource(R.drawable.ic_guide_1)
                titleText.text = getString(R.string.first_page_title)
                descriptionText.text = getString(R.string.first_page_text)
            }
            1 -> binding.apply {
                shapeableImageView.setImageResource(R.drawable.ic_guide_2)
                titleText.text = getString(R.string.second_page_title)
                descriptionText.text = getString(R.string.second_page_text)
            }
            2 -> binding.apply {
                shapeableImageView.setImageResource(R.drawable.ic_guide_3)
                titleText.text = getString(R.string.third_page_title)
                descriptionText.text = getString(R.string.third_page_text)
            }
            3 -> binding.apply {
                shapeableImageView.setImageResource(R.drawable.ic_guide_4)
                titleText.text = getString(R.string.fourth_page_title)
                descriptionText.text = getString(R.string.fourth_page_text)
            }
            4 -> binding.apply {
                shapeableImageView.setImageResource(R.drawable.ic_guide_5)
                titleText.text = getString(R.string.fifth_page_title)
                descriptionText.text = getString(R.string.fifth_page_text)
            }
            5 -> binding.apply {
                shapeableImageView.setImageResource(R.drawable.ic_guide_6)
                titleText.text = getString(R.string.sixth_page_title)
                descriptionText.text = getString(R.string.sixth_page_text)
                //descriptionText.textSize = resources.getDimension(R.dimen.text_small)
                descriptionText.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_small))
            }
            6 -> binding.apply {
                shapeableImageView.setImageResource(R.drawable.ic_guide_7)
                titleText.text = getString(R.string.seventh_page_title)
                descriptionText.text = getString(R.string.seventh_page_text)
            }
        }
    }

    companion object{
        fun newInstance(param1 : Int) =
            Bundle().apply {
                putInt(ARG_PARAM1, param1)
            }
    }
}
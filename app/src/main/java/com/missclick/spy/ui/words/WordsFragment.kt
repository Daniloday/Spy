package com.missclick.spy.ui.words

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import by.kirich1409.viewbindingdelegate.viewBinding
import com.missclick.spy.R
import com.missclick.spy.adapters.WordsListAdapter
import com.missclick.spy.data.models.WordListModel
import com.missclick.spy.databinding.FragmentWordsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


const val SET = "name"
const val NEW = "new"

class WordsFragment : Fragment(R.layout.fragment_words) {
    val binding by viewBinding(FragmentWordsBinding::bind)
    private val viewModel : WordsViewModel by viewModel()

    var setName : String? = null
    var newSet : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            setName = getString(SET)
            newSet = getBoolean(NEW)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textSetName.setText(setName)
        if(setName == getString(R.string.basic)) {
            binding.imagePen.visibility = View.INVISIBLE
            binding.imageGarbage.visibility = View.INVISIBLE
        }
        val adapter =  WordsListAdapter()
        var add = true
        var edit = false
        if(newSet!!){
            binding.imagePen.setImageResource(R.drawable.ic_save)
            edit = true
            binding.textSetName.isEnabled = true
            binding.textSetName.requestFocus()
            binding.textSetName.setSelection(binding.textSetName.length())
            val a = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            a.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
        viewModel.getWords(setName!!).observe(viewLifecycleOwner){ it ->
            var words = it.map {
                WordListModel(word = it)
            }.toMutableList()

            adapter.setData(words)
            adapter.setOnClickListener {
                if(it.editable){
                    val word = WordListModel(word = words.last().word)
                    words.remove(words.last())
                    if(word.word != ""){
                        words.add(word)
                        viewModel.addWords(words = listOf(word),category = binding.textSetName.text.toString())
                    }
                    adapter.updateWordListItems(words)
                    add = true
                    (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).
                    hideSoftInputFromWindow(view.windowToken, 0)
                }
                else{
                    words.remove(it)
                    adapter.updateWordListItems(words)
                    Log.e(binding.textSetName.text.toString(),it.word)
                    viewModel.removeWord(word = it,category = binding.textSetName.text.toString())
                }
            }
            binding.recycleWords.adapter = adapter
            binding.recycleWords.layoutManager = LinearLayoutManager(requireActivity())
            binding.cardAdd.setOnClickListener {
                if(add){
                    edit = false
                    binding.textSetName.isEnabled = false
                    add = false
                    binding.imagePen.setImageResource(R.drawable.ic_pen)
                    Log.e("words",words.toString())
                    if(binding.textSetName.text.toString() == "")
                        binding.textSetName.setText(getString(R.string.new_set))
                    viewModel.updateSetName(oldSetName = setName!!,newSetName = binding.textSetName.text.toString(),data = words.toList())
                    words.add(WordListModel("",true))
                    adapter.updateWordListItems(words)
                    binding.recycleWords.scrollToPosition(adapter.itemCount - 1)
                    val a = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    a.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                }
            }

            binding.imagePen.setOnClickListener {
                if(edit){
                    binding.imagePen.setImageResource(R.drawable.ic_pen)
                    edit = false
                    if(binding.textSetName.text.toString() == "")
                        binding.textSetName.setText(getString(R.string.new_set))
                    viewModel.updateSetName(oldSetName = setName!!,newSetName = binding.textSetName.text.toString(),data = words)
                    binding.textSetName.isEnabled = false
                    setName = binding.textSetName.text.toString()
                    (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).
                    hideSoftInputFromWindow(view.windowToken, 0)
                }
                else{
                    binding.imagePen.setImageResource(R.drawable.ic_save)
                    edit = true
                    if(words.last().editable){
                        add = true
                        words.remove(words.last())
                        adapter.updateWordListItems(words)
                    }
                    binding.textSetName.isEnabled = true
                    binding.textSetName.requestFocus()
                    binding.textSetName.setSelection(binding.textSetName.length())
                    val a = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    a.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                }


            }
        }
        binding.imageGarbage.setOnClickListener {
            viewModel.set.asLiveData().observe(viewLifecycleOwner){
                if(it == setName!!)
                    viewModel.chooseSet(getString(R.string.basic))
            }
            viewModel.removeWordsInCategory(category = setName!!)
            findNavController().navigateUp()
            val a = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            a.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        fun quit(){
            viewModel.set.asLiveData().observe(viewLifecycleOwner){
                if(it == setName!! && adapter.getList().size == 0)
                    viewModel.chooseSet(getString(R.string.basic))
            }
            findNavController().navigateUp()
            val a = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            a.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        binding.appCompatImageButton.setOnClickListener {
            quit()
        }
        binding.appCompatImageButton1.setOnClickListener {
            quit()
        }

        binding.buttonChoose.setOnClickListener {
            val data = adapter.getList()
            val oldName = setName!!
            val newName = binding.textSetName.text.toString()
            if(data.size > 1){
                viewModel.chooseSet(newName)
                findNavController().navigate(R.id.action_wordsFragment_to_menuFragment)
            }
            else{
                if (data.size == 1 && !data[0].editable){
                    viewModel.chooseSet(newName)
                    findNavController().navigate(R.id.action_wordsFragment_to_menuFragment)
                }else{
                    val toast = Toast.makeText(
                        context,
                        getString(R.string.toast_empty_set),
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }

            }
        }

    }
    

    override fun onPause() {
        super.onPause()
        val a = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        a.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    companion object{
        fun newInstance(setName: String, newSet : Boolean) =
                Bundle().apply {
                    putString(SET, setName)
                    putBoolean(NEW, newSet)
                }
    }
}
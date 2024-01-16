package com.missclick.spy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.missclick.spy.R
import com.missclick.spy.adapters.diff.DiffUtilWordsCallback
import com.missclick.spy.data.models.CollectionsModel
import com.missclick.spy.data.models.WordListModel
import com.missclick.spy.data.models.WordsModel
import com.missclick.spy.databinding.CollectionsListItemBinding
import com.missclick.spy.databinding.WordsListItemsBinding

class WordsListAdapter(

) : RecyclerView.Adapter<WordsListAdapter.WordsViewHolder>(){

    private val items = mutableListOf<WordListModel>()
    private var onClickListener : ((WordListModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder =
            WordsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.words_list_items, parent, false))

    override fun getItemCount(): Int = items.size

    fun setData(newList : List<WordListModel>){
        items.clear()
        items.addAll(newList)
    }

    fun updateWordListItems(newItems : List<WordListModel>){
        val diffUtilCallback = DiffUtilWordsCallback(oldWordList = items, newWordList = newItems)
        val diffRes = DiffUtil.calculateDiff(diffUtilCallback)
        items.clear()
        items.addAll(newItems)
        diffRes.dispatchUpdatesTo(this)
    }

    fun getList() = mutableListOf<WordListModel>().apply { addAll(items) }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(items[position])

    }

    fun setOnClickListener(callback : (WordListModel) -> Unit){
        onClickListener = callback
    }

    inner class WordsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding by viewBinding(WordsListItemsBinding::bind)
        fun bind(item : WordListModel){
            binding.textWord.setText(item.word)
            binding.textWord.isEnabled = item.editable
            binding.textWord.requestFocus()
            if(item.editable) binding.imageGarbage.setImageResource(R.drawable.ic_save)
            binding.imageGarbage.setOnClickListener {
                item.word = binding.textWord.text.toString()
                onClickListener?.invoke(item)
            }
        }
    }

}

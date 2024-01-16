package com.missclick.spy.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.missclick.spy.data.models.WordListModel

class DiffUtilWordsCallback(
        private val oldWordList : List<WordListModel>,
        private val newWordList : List<WordListModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWordList[oldItemPosition] == newWordList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldWordList.size
    }

    override fun getNewListSize(): Int {
        return newWordList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWordList[oldItemPosition].word == newWordList[newItemPosition].word
    }
}

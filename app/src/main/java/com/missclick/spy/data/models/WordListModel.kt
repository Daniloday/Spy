package com.missclick.spy.data.models

import android.text.Editable

data class WordListModel(
        var word : String,
        val editable: Boolean = false
)
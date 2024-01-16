package com.missclick.spy.data.models

import java.io.Serializable

data class GameParams(
        val players : Int,
        val spy: Int,
        val time: Int,
        val category: String
                      ) : Serializable

package com.example.setgame.ui

import com.example.setgame.API.Card

data class GameUIState(
    var field: ArrayList<Card> = ArrayList(81),
    var chosenCards: ArrayList<Int> = ArrayList(3),
    var score: Int = 0
)

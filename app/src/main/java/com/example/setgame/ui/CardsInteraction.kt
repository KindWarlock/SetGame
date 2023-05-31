package com.example.setgame.ui

import com.example.setgame.API.Card

interface CardsInteraction {
    fun onCardChosen(card: Card)
}
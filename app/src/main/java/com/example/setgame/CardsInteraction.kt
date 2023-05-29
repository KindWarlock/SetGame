package com.example.setgame

import com.example.setgame.API.Card

interface CardsInteraction {
    fun onCardChosen(card: Card)
}
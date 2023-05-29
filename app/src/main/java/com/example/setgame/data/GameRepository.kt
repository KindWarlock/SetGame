package com.example.setgame.data

import com.example.setgame.API.Card
import com.example.setgame.API.NetHandler
import com.example.setgame.API.SetRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameRepository(private val token: String) {
    val field = ArrayList<Card>(81)
    var score: Int = 0

    suspend fun getField() {
        withContext(Dispatchers.IO) {
            val response = NetHandler.setApi.getField(SetRequest(accessToken = token)).execute()
            val data = response.body()!!
            field.clear()
            field.addAll(data.cards)
            updateScore(score)
        }
    }

    suspend fun chooseSet(cards: ArrayList<Int>): Boolean {
        val data = withContext(Dispatchers.IO) {
            val response = NetHandler.setApi.pick(SetRequest(accessToken = token, cards = cards)).execute()
            response.body()!!
        }
        withContext(Dispatchers.IO) {
            updateScore(score)

            // updating field
            if (data.isSet) {
                if (field.size - 3 < 12) {
                    addCards()
                }
                getField()
            }
        }
        return data.isSet
    }

    suspend fun addCards() {
        withContext(Dispatchers.IO) {
            NetHandler.setApi.addCards(SetRequest(accessToken = token)).execute()
            getField()
        }
    }

    private fun updateScore(newScore: Int) {
        score = newScore
    }
}
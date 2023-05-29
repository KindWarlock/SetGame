package com.example.setgame.API

data class SetRequest(
    val accessToken: String,
    val gameId: Int? = null,
    val cards: ArrayList<Int>? = null
)

data class Login(
    val nickname: String,
    val password: String
)

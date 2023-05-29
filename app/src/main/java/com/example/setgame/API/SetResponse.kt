package com.example.setgame.API


data class SetResponse(
    val success: Boolean,
    val error: ExceptionMsg?,
    val nickname: String? = null,
    val accessToken: String? = null,
    val gameId: Int? = null,
    val games: ArrayList<Game>? = null,
    val users: List<User>? = null
)

data class ExceptionMsg(
    val message: String
)

data class Field(
    val cards: ArrayList<Card>,
    val status: String,
    val score: Int
)

data class Game(
    val gameId: Int,
    val status: String
)

data class CheckSet(
    val isSet: Boolean,
    val score: Int
)

data class User(
    val name: String,
    val score: Int
)
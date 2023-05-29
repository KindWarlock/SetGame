package com.example.setgame.API

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SetAPI {
    @POST("/user/register")
    fun register(@Body login: Login): Call<SetResponse>

    @POST("/set/room/create")
    fun create(@Body request: SetRequest): Call<SetResponse>

    @POST("/set/room/list")
    fun getGames(@Body request: SetRequest): Call<SetResponse>

    @POST("/set/room/enter")
    fun enterRoom(@Body request: SetRequest): Call<SetResponse>

    @POST("/set/field")
    fun getField(@Body request: SetRequest): Call<Field>

    @POST("/set/pick")
    fun pick(@Body request: SetRequest): Call<CheckSet>

    @POST("/set/add")
    fun addCards(@Body request: SetRequest): Call<SetResponse>

    @POST("/set/scores")
    fun getScores(@Body request: SetRequest): Call<SetResponse>

    @POST("/set/room/leave")
    fun leave(@Body request: SetRequest): Call<SetResponse>
}
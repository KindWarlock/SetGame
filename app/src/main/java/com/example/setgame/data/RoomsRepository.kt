package com.example.setgame.data

import android.util.Log
import com.example.setgame.API.Game
import com.example.setgame.API.NetHandler
import com.example.setgame.API.SetRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomsRepository(private val token: String) {
    var rooms = ArrayList<Game>()
    var inRoom = false

    suspend fun createRoom(): Int {
        val id = withContext(Dispatchers.IO) {
            val response = NetHandler.setApi.create(SetRequest(accessToken = token)).execute()
            val data = response.body()!!
            Log.d("TAG", "$data")
            rooms.add(Game(data.gameId!!, "ongoing"))
            data.gameId
        }
        return id
    }

    suspend fun getList() {
        withContext(Dispatchers.IO) {
            val response = NetHandler.setApi.getGames(SetRequest(accessToken = token)).execute()
            val data = response.body()!!
            Log.d("TAG", "$data")
            val newRooms = ArrayList<Game>(data.games!!)
            rooms = newRooms
//            rooms.clear()
//            rooms.addAll(data.games!!)
            Log.d("TAG", "$rooms")
        }
    }

    suspend fun enterRoom(id: Int) {
        withContext(Dispatchers.IO) {
            val response =
                NetHandler.setApi.enterRoom(SetRequest(accessToken = token, gameId = id)).execute()
            val data = response.body()!!
            Log.d("TAG", "$data")
            inRoom = true
        }
    }

    suspend fun leaveRoom() {
        withContext(Dispatchers.IO) {
            val response = NetHandler.setApi.leave(SetRequest(accessToken = token)).execute()
            val data = response.body()!!

            inRoom = false
        }
    }
}
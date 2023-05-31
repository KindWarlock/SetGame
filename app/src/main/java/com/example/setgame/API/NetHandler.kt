package com.example.setgame.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetHandler {

    companion object {
        val retrofit by lazy {
            with(Retrofit.Builder()) {
                baseUrl("http://192.168.1.41:5000/")
                addConverterFactory(GsonConverterFactory.create())
                build()
            }
        }

        val setApi by lazy {
            retrofit.create(SetAPI::class.java)
        }
    }

}
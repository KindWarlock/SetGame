package com.example.setgame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.setgame.API.Login
import com.example.setgame.API.NetHandler.Companion.setApi
import com.example.setgame.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var binding: ActivityMainBinding

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
//        sharedPref.edit().remove("accessToken").commit();
        if (sharedPref.getString("accessToken", "") != "") {
            listRooms()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.regButton.setOnClickListener {
            val name = binding.name.toString()
            val pwd = binding.pwd.toString()
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("tag", "$name")
                val response = setApi.register(Login(name, pwd)).execute()
                Log.d("TAG", "${ response.body() }")
                if (response.body()!!.success) {
                    val sharedPref = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putString("accessToken", response.body()!!.accessToken)
                        apply()
                    }
                    listRooms()
                }
            }
        }
    }

    fun listRooms() {
        val intent = Intent(this, RoomsActivity::class.java)
        startActivity(intent)
    }
}
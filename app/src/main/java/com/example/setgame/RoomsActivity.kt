package com.example.setgame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.setgame.ui.RoomsAdapter
import com.example.setgame.ui.RoomsInteraction
import com.example.setgame.ui.RoomsViewModel
import kotlinx.coroutines.launch

class RoomsActivity : AppCompatActivity(), RoomsInteraction {
    private lateinit var viewModel: RoomsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        val token = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
            .getString("accessToken", "") ?: ""
        val _viewModel: RoomsViewModel by viewModels { RoomsViewModel.RoomsViewModelFactory(token) }
        viewModel = _viewModel

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = RoomsAdapter(ArrayList(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.list = it.rooms
                    adapter.notifyDataSetChanged()
                }
            }
        }
        viewModel.getRooms()

        findViewById<Button>(R.id.createButton).setOnClickListener {
            Log.d("TAG", "Click!")
            lifecycleScope.launch {
                val id = viewModel.createRoom()
                Log.d("TAG", "$id")
                onRoomChosen(id)
            }
        }
    }

    override fun onRoomChosen(id: Int) {
        viewModel.enterRoom(id)
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("gameId", id)
        startActivity(intent)
    }
}
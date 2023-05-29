package com.example.setgame.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.setgame.data.RoomsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomsViewModel(token: String) : ViewModel() {
    private val repository = RoomsRepository(token)

    class RoomsViewModelFactory(private val token: String): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(RoomsViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return RoomsViewModel(token) as T
            }
            throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
        }
    }

    private val _uiState = MutableStateFlow(RoomsUIState())
    val uiState: StateFlow<RoomsUIState> = _uiState.asStateFlow()

    suspend fun createRoom(): Int {
        val id = repository.createRoom()
        repository.enterRoom(id)
        return id
    }

    fun getRooms() {
        viewModelScope.launch {
            repository.getList()
            _uiState.update {
                it.copy(rooms = repository.rooms)
            }
        }
    }

    fun enterRoom(id: Int) {
        viewModelScope.launch {
            repository.enterRoom(id = id)
        }
    }
}
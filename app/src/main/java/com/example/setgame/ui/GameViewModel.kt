package com.example.setgame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.setgame.data.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.setgame.data.RoomsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class GameViewModel(private val token: String): ViewModel() {
    private val gameRepository = GameRepository(token)
    private val roomsRepository = RoomsRepository(token)

    class GameViewModelFactory(private val token: String): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(GameViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return GameViewModel(token) as T
            }
            throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
        }
    }

    private val _uiState = MutableStateFlow(GameUIState())
    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()

    fun getField() {
        viewModelScope.launch {
            gameRepository.getField()
            _uiState.update {
                it.copy(field = gameRepository.field,
                        score = gameRepository.score)
            }
        }
    }

    fun chooseSet() {
        viewModelScope.launch {
            gameRepository.chooseSet(uiState.first().chosenCards)
            _uiState.update {
                it.copy(field = gameRepository.field,
                        score = gameRepository.score)
            }
        }
    }

    fun pickCard(id: Int) {
        viewModelScope.launch {
            val cards: ArrayList<Int> = ArrayList(3)
            _uiState.update {
                _uiState.first().chosenCards.add(id)
                cards.addAll(_uiState.first().chosenCards)
                it.copy(chosenCards = cards)
            }
            if (cards.size == 3) {
                chooseSet()
            }
        }
    }

    suspend fun leaveRoom() {
        roomsRepository.leaveRoom()
    }
}

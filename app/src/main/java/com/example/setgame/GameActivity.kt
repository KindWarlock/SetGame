package com.example.setgame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TableRow
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.setgame.API.Card
import com.example.setgame.databinding.ActivityGameBinding
import com.example.setgame.ui.CardView
import com.example.setgame.ui.CardsInteraction
import com.example.setgame.ui.GameViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity(), CardsInteraction {
    private lateinit var binding: ActivityGameBinding

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val id = intent.getIntExtra("gameId", -1)
        val token = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
            .getString("accessToken", "") ?: ""

        binding.cards.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            Log.d("TAG", "Layout changed")
            binding.cards.requestLayout()
        }
        val _viewModel: GameViewModel by viewModels{ GameViewModel.GameViewModelFactory(token) }
        viewModel = _viewModel

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    initTable(it.field)
                    binding.score.text = resources.getString(R.string.score,  it.score)
                }
            }
        }
        viewModel.getField()
    }

    override fun onCardChosen(card: Card) {

    }

    private fun initTable(field: ArrayList<Card>) {
        binding.cards.removeAllViews()
        for (i in 0 until (field.size / 3)) {
            val newRow = TableRow(this)
            newRow.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            for (j in 0 until 3) {
                val card = CardView(this)
                val idx = 3 * i + j
                card.count = field[idx].count
                card.shape = field[idx].shape
                card.color = field[idx].color
                card.fill = field[idx].fill
                newRow.addView(card, j)
            }
            binding.cards.addView(newRow, i)
        }
        binding.cards.requestLayout()
//        Log.d("TAG", "${binding.cards.getChildAt(3).width}")
    }

    override fun onDestroy() {
        GlobalScope.launch {
            viewModel.leaveRoom()
        }
        super.onDestroy()

    }
}
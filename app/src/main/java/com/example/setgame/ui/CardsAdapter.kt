package com.example.setgame.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.setgame.API.Card
import com.example.setgame.CardsInteraction

class CardsAdapter(val list: ArrayList<Card>, val listener: CardsInteraction): RecyclerView.Adapter<CardsAdapter.ViewHolder>() {
    inner class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        private lateinit var card: Card

        init {
            cardView.setOnClickListener {
                listener.onCardChosen(card)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardView.color = list[position].color
        holder.cardView.count = list[position].count
        holder.cardView.fill = list[position].fill
        holder.cardView.shape = list[position].shape
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(100, 150)
        return ViewHolder(view)
    }
}
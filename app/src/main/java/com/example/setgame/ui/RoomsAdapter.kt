package com.example.setgame.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.setgame.API.Game
import com.example.setgame.R
import com.example.setgame.RoomsInteraction
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RoomsAdapter(var list: ArrayList<Game>, val listener: RoomsInteraction): RecyclerView.Adapter<RoomsAdapter.ViewHolder>(){
    inner class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var id: TextView

        init {
            itemView.setOnClickListener {
                listener.onRoomChosen(id.text.toString().toInt())
            }
            id = itemView.findViewById(R.id.name)
        }

        fun setBackground(available: Boolean) {
            if (available) {
                itemView.background = itemView.context.getDrawable(R.drawable.available_background)
            } else {
                itemView.background = itemView.context.getDrawable(R.drawable.unavailable_background)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = list[position].gameId.toString()
        val available = list[position].status == "ongoing"
        holder.setBackground(available)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.room_item, parent, false)
        return ViewHolder(itemView)
    }

}
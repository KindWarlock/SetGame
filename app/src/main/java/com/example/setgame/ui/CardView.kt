package com.example.setgame.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.children
import com.example.setgame.R

class CardView(context: Context, attrs: AttributeSet? = null): LinearLayout(context, attrs) {
    var shape: Int
    var color: Int
    var count: Int
    var fill: Int

    val imgs = ArrayList<ImageView>(3)

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        context.obtainStyledAttributes(attrs, R.styleable.CardView).let {
            shape = it.getInt(R.styleable.CardView_shape, 0)
            color = it.getInt(R.styleable.CardView_shape_color, 0)
            count = it.getInt(R.styleable.CardView_count, 0)
            fill = it.getInt(R.styleable.CardView_fill, 0)
            it.recycle()
        }
        for (i in 0 until imgs.size) {
            imgs[i] = ImageView(context)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        for (i in 0 until count) {
            val ellipseId = when (fill) {
                FILL_PATTERN -> R.drawable.ellipse_p
                FILL_FILLED -> R.drawable.ellipse_f
                else -> R.drawable.ellipse
            }
            val rombId = when (fill) {
                FILL_PATTERN -> R.drawable.romb_p
                FILL_FILLED -> R.drawable.romb_f
                else -> R.drawable.romb
            }
            val waveId = when (fill) {
                FILL_PATTERN -> R.drawable.wave_p
                FILL_FILLED -> R.drawable.wave_f
                else -> R.drawable.wave
            }

            imgs[i].setImageResource(
                when (shape) {
                    SHAPE_ELLIPSE -> ellipseId
                    SHAPE_ROMB -> rombId
                    else -> waveId
                }
            )

            imgs[i].colorFilter = when (color) {
                COLOR_RED -> PorterDuffColorFilter(
                    context.getColor(R.color.red),
                    PorterDuff.Mode.SRC_ATOP
                )

                COLOR_GREEN -> PorterDuffColorFilter(
                    context.getColor(R.color.green),
                    PorterDuff.Mode.SRC_ATOP
                )

                COLOR_BLUE -> PorterDuffColorFilter(
                    context.getColor(R.color.blue),
                    PorterDuff.Mode.SRC_ATOP
                )

                else -> PorterDuffColorFilter(
                    context.getColor(R.color.black),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
            imgs[i].layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, 100)
            addView(imgs[i])
        }
    }

    companion object {
        const val COLOR_GREEN = 2
        const val COLOR_BLUE = 3
        const val COLOR_RED = 1

        const val SHAPE_ELLIPSE = 1
        const val SHAPE_ROMB = 2
        const val SHAPE_WAVE = 3

        const val FILL_NONE = 1
        const val FILL_FILLED = 2
        const val FILL_PATTERN = 3
    }
}
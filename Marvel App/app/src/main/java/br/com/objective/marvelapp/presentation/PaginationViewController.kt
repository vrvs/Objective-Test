package br.com.objective.marvelapp.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import br.com.objective.marvelapp.R

class PaginationViewController(view: View, val context: Context, val listener: OnListPaginationInteractionListener) {

    private val previous: ImageView = view.findViewById(R.id.pagePrevious)
    private val left: TextView = view.findViewById(R.id.pageLeft)
    private val center: TextView = view.findViewById(R.id.pageCenter)
    private val right: TextView = view.findViewById(R.id.pageRight)
    private val next: ImageView = view.findViewById(R.id.pageNext)

    init {
        previous.setOnClickListener {
            listener.onPreviousInteraction()
        }
        next.setOnClickListener {
            listener.onNextInteraction()
        }
    }

    fun setAll(offset: Int, total: Int, limit: Int) {
        when {
            total == 0 -> {
                setCenter(0, false)
                setLeft(0, false)
                setRight(0, false)
                setPrevious(false)
                setNext(false)
            }
            offset < limit -> {
                setLeft(offset/4, true)
                select(left)
                setCenter((offset/4)+1, true)
                unselect(center)
                setRight((offset/4)+2, true)
                unselect(right)
            }
            total - offset <= limit && total > 2*limit -> {
                setLeft((offset/4)-2, true)
                unselect(left)
                setCenter((offset/4)-1, true)
                unselect(center)
                setRight(offset/4, true)
                select(right)
            }
            else -> {
                setLeft((offset/4)-1, true)
                unselect(left)
                setCenter(offset/4, true)
                select(center)
                setRight((offset/4)+1, true)
                unselect(right)
            }
        }

        if (total <= limit) {
            setCenter(0, false)
        }

        if (total <= 2*limit) {
            setRight(0, false)
        }

        if (total > 0 && offset >= limit) {
            setPrevious(true)
        } else {
            setPrevious(false)
        }
        if (total - offset > limit) {
            setNext(true)
        } else {
            setNext(false)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun select(textView: TextView) {
        textView.isClickable = false
        textView.background = context.getDrawable(R.drawable.circle)
        textView.setTextColor(context.getColor(R.color.white))
    }

    @SuppressLint("ResourceAsColor")
    private fun unselect(textView: TextView) {
        textView.isClickable = true
        textView.background = context.getDrawable(R.drawable.circle_white)
        textView.setTextColor(context.getColor(R.color.red))
    }

    private fun setTextView(textView: TextView, value: Int, visible: Boolean) {
        if (visible) {
            textView.isClickable = true
            textView.visibility = VISIBLE
            textView.text = (value+1).toString()
        } else {
            textView.isClickable = false
            textView.visibility = INVISIBLE
        }
    }

    private fun setLeft(value: Int, visible: Boolean) {
        setTextView(left, value, visible)
    }

    private fun setCenter(value: Int, visible: Boolean) {
        setTextView(center, value, visible)
    }

    private fun setRight(value: Int, visible: Boolean) {
        setTextView(right, value, visible)
    }

    private fun setImageView(imageView: ImageView, visible: Boolean) {
        if (visible) {
            imageView.visibility = VISIBLE
            imageView.isClickable = true
        } else {
            imageView.visibility = INVISIBLE
            imageView.isClickable = false
        }
    }

    private fun setPrevious(visible: Boolean) {
        setImageView(previous, visible)
    }

    private fun setNext(visible: Boolean) {
        setImageView(next, visible)
    }
}
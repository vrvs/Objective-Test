package br.com.objective.marvelapp.presentation

import android.graphics.BitmapFactory
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import br.com.objective.marvelapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.util.concurrent.atomic.AtomicInteger


class HeroViewController(val view: View) {

    private val imageView: ImageView = view.findViewById(R.id.imageView)
    private val title: TextView = view.findViewById(R.id.title)
    private val countBefore: AtomicInteger = AtomicInteger(0)
    private val countAfter: AtomicInteger = AtomicInteger(0)

    fun changeVisibility(visible: Boolean) {
        if (visible) {
            imageView.visibility = VISIBLE
            title.visibility = VISIBLE
        } else {
            imageView.visibility = INVISIBLE
            title.visibility = INVISIBLE
        }
    }

    fun setTitle(title: String) {
        this.title.text = title
    }

    fun setImage(path: String, extension: String) {
         Glide.with(view)
            .load("$path.$extension")
            .thumbnail(0.05f)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView);
    }
}
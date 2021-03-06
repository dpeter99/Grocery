package com.aper_lab.grocery.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.aper_lab.grocery.R
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe
import com.bumptech.glide.Glide
import org.threeten.bp.Duration

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("time")
fun bindImage(textView: TextView, time: Long?) {
    time?.let {
        val text: String = Duration.ofSeconds(time).toMinutes().toString() + textView.context.resources.getString(R.string.minute_short)
        textView.text = text;
    }
}

@BindingAdapter("visible")
fun ImageView.setVisibility(item: Boolean?) {
    item?.let {
        visibility = if(it){
            View.VISIBLE;
        } else {
            View.INVISIBLE;
        }
    }
}

@BindingAdapter("stepNumber")
fun TextView.setSleepDurationFormatted(item: Int?) {
    item?.let {
        text = item.toString();
    }
}

@BindingAdapter("recipeSourceIcon")
fun AppCompatImageView.setrecipeIcon(item: Recipe?) {
    item?.let {
        if(item.link != "") {
            val id = RecipeAPIService.getSourceIDfromURL(item.link);
            this.setImageResource(
                this.context.resources.getIdentifier(
                    "icon_" + id,
                    "drawable",
                    "com.aper_lab.grocery"
                )
            )
        }
    }
}

@BindingAdapter("recipeName")
fun TextView.setSleepImage(item: Recipe?) {
    item?.let {
        text = item.name;
    }
}
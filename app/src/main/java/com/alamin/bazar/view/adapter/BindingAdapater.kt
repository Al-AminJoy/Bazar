package com.alamin.bazar.view.adapter

import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import coil.load
import com.alamin.bazar.R

@BindingAdapter("setImage")
fun ImageView.setImage(url: String){
    this.load(url)
}

@BindingAdapter("setOrderCardBackground")
fun CardView.setOrderCardBackground(status: String){
    if (status.equals("pending",ignoreCase = true)){
        this.setCardBackgroundColor(resources.getColor(R.color.Bee_Yellow))
    }else if (status.equals("approved",ignoreCase = true)){
        this.setCardBackgroundColor(resources.getColor(R.color.theme_secondary))
    }else if (status.equals("complete",ignoreCase = true)){
        this.setCardBackgroundColor(resources.getColor(R.color.Algae_Green))
    }else if (status.equals("canceled",ignoreCase = true)){
        this.setCardBackgroundColor(resources.getColor(R.color.Ferrari_Red))
    }
}

@BindingAdapter("setIconTint")
fun ImageView.setIconTint(isWished: Boolean){
    if (isWished){
        setColorFilter(resources.getColor(R.color.red))
    }else{
        setColorFilter(resources.getColor(R.color.Ash_Gray))
    }
}
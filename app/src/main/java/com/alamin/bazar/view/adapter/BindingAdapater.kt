package com.alamin.bazar.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("setImage")
fun ImageView.setImage(url: String){
    this.load(url)
}
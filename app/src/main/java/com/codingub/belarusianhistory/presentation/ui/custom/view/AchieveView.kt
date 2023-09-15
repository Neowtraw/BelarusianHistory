package com.codingub.belarusianhistory.presentation.ui.custom.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.codingub.belarusianhistory.domain.model.Achieves.Achieve
import com.codingub.belarusianhistory.utils.extension.dp

@SuppressLint("ViewConstructor")
class AchieveView(
    context: Context
) : ViewGroup(context){

    var achieve: Achieve? = null
        set(value) {
            value?.let {

            } ?: run{

            }
            requestLayout()
            field = value
        }

    //private val imageView: ImageView
    //private val tvName: TextView
    //private val tvInfo: TextView

    private val indent: Int = 16.dp
    private val textIndent: Int = 8.dp


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("Not yet implemented")
    }
}

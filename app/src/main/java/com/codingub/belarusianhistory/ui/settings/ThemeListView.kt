package com.codingub.belarusianhistory.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.sdk.ThemeType
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp


@SuppressLint("ViewConstructor")
class ThemeListView(
    private val lastThemePos: Int,
    private val themes: List<ThemeType>,
    context: Context,
    private val onItemSelect: (ThemeType) -> Unit
    ) : LinearLayoutCompat(context) {

    private lateinit var themesView: RecyclerView
    private lateinit var themeAdapter: ThemeAdapter

    private val rect: RectF = RectF()
    private val backgroundPaint: Paint = Paint()
    private val cornerRadius = 27f.dp


    init{
        setWillNotDraw(false)

        backgroundPaint.color = Resource.color(R.color.bg_btn)
        orientation = VERTICAL


        createThemesView()
        addView(themesView, LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ))
    }


    private fun createThemesView(){
        themesView = RecyclerView(context).apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(context)
            themeAdapter = ThemeAdapter(themes, lastThemePos){
                onItemSelect.invoke(it)
            }
            adapter = themeAdapter
        }
    }

    override fun onDraw(canvas: Canvas) {
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, backgroundPaint)
        super.onDraw(canvas)
    }
}
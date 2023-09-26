package com.codingub.belarusianhistory.ui.custom

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.os.Handler
import android.os.Looper


class ScaleTouchListener : View.OnTouchListener {

    private var view: View? = null

    private val DURATION: Long = 100L
    private val SCALE_PRESSED : Float = 0.97f
    private val LONG_PRESS_TIME: Long = 250

    private lateinit var scaleAnimator: ValueAnimator

    private val longPressHandler: Handler = Handler(Looper.getMainLooper())

    private fun cancelPress(){
        if(!wasPressed) return
        wasPressed = false

        scaleAnimator.apply {
            cancel()
            setFloatValues(view!!.scaleX, SCALE_PRESSED, 1f)
            start()
        }
        longPressHandler.removeCallbacksAndMessages(null)

    }

    private var wasPressed: Boolean = false
    private var wasLongClicked: Boolean = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if(view == null){
            view = v
            createAnimators()
        }

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                //при нажатии на экран
                wasPressed = true

                wasLongClicked = false
                longPressHandler.postDelayed({
                    wasLongClicked = v.performLongClick()
                    if(wasLongClicked){
                        cancelPress()
                    }
                }, LONG_PRESS_TIME)

                scaleAnimator.apply {
                    cancel()
                    setFloatValues(1f, SCALE_PRESSED)
                    start()
                }
            }
            MotionEvent.ACTION_UP -> {
                if(isEventInside(event) && !wasLongClicked){
                    v.performClick()
                    cancelPress()
                }
            }
            MotionEvent.ACTION_CANCEL ->{
                if(!wasLongClicked){
                    cancelPress()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if(!isEventInside(event) && !wasLongClicked){
                    cancelPress()
                }
            }
        }
        return true
    }

    private fun createAnimators(){
        scaleAnimator = ValueAnimator().apply {
            duration = DURATION

            addUpdateListener {
                val scale = it.animatedValue as Float
                view!!.scaleX = scale
                view!!.scaleY = scale
            }
        }
    }


    private fun isEventInside(e: MotionEvent): Boolean{
        val rect = Rect(
            view!!.left,
            view!!.top,
            view!!.right,
            view!!.bottom
        )

        return rect.contains(
            view!!.left + e.x.toInt(),
            view!!.top + e.y.toInt()
        )
    }
}
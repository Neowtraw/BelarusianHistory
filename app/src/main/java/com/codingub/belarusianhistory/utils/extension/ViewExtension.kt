package com.codingub.belarusianhistory.utils.extension

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.text.Editable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.codingub.belarusianhistory.ui.custom.ScaleTouchListener

/*
    View
 */
fun View.setPaddingDp(left: Int, top: Int, right: Int, bottom: Int) {
    setPadding(left.dp, top.dp, right.dp, bottom.dp)
}

fun View.applyScaleTouch() = setOnTouchListener(ScaleTouchListener())

/*
    TextView
 */

var TextView.textSizeDp: Float
    get() = textSize.px
    set(value) = setTextSize(TypedValue.COMPLEX_UNIT_DIP, value)

fun View.withTypedArray(
    set: AttributeSet?,
    @StyleableRes attrs: IntArray,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
    action: TypedArray.() -> Unit
) {
    val typedArray = context.theme.obtainStyledAttributes(
        set, attrs, defStyleAttr, defStyleRes
    )
    action(typedArray)
    typedArray.recycle()
}

@ColorInt
fun View.resolveColor(@ColorRes colorRes: Int): Int =
    ResourcesCompat.getColor(this.context.resources, colorRes, null)

// walking through a hierarchy of views
fun View.forEach(action: View.(Int) -> Unit) = forEach(0, action)

private fun View.forEach(level: Int = 0, action: View.(Int) -> Unit) {
    this.action(level)
    if (this is ViewGroup) {
        this.children.forEach { it.forEach(level + 1, action) }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : View> ViewGroup.findChildren(klazz: Class<*>, recursive: Boolean = true): List<T> {
    val childrenOfType = mutableListOf<T>()
    val childCnt = this.childCount
    for (i in 0 until childCnt) {
        val child = this.getChildAt(i)
        if (klazz.isAssignableFrom(child.javaClass)) {
            childrenOfType.add(child as T)
        }
        if (recursive && child is ViewGroup) {
            childrenOfType.addAll(child.findChildren(klazz, recursive))
        }
    }
    return childrenOfType
}

fun <T : View> ViewGroup.removeView(id: Int) {
    val v = findViewById<T>(id) ?: return
    this.removeView(v)
}

fun <T : View> ViewGroup.removeViews(id: Int) {
    do {
        val v = findViewById<T>(id) ?: return
        this.removeView(v)
    } while (true)
}

fun String.toEditable(): Editable =
    Editable.Factory.getInstance().newEditable(this)

fun ImageView.setTint(@ColorRes color: Int) {
    this.imageTintList = ColorStateList.valueOf(
        resolveColor(color)
    )
}

fun ConstraintLayout.updateConstraints(action: ConstraintSet.() -> Unit) {
    ConstraintSet().apply {
        clone(this@updateConstraints)
        action(this)
        applyTo(this@updateConstraints)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, K> MutableList<T>.addAllDistinct(
    other: List<T>,
    distinctPredicate: (T) -> K = { it as K },
    onRepeat: (T, T) -> T = { f, s -> f },
    fromStart: Boolean = false
) {
    val added = this.map(distinctPredicate).toMutableSet()

    for (item in other) {
        val k = distinctPredicate(item)
        if (added.add(k)) {
            // adding as new item
            if (fromStart) {
                this.add(0, item)
            } else {
                this.add(item)
            }
        } else {
            // duplicate, try resolve
            val indexOfExisting = this.indexOfFirst { distinctPredicate(it) == k }
            if (indexOfExisting != -1) {
                this[indexOfExisting] = onRepeat(this[indexOfExisting], item)
            }
        }
    }
}


fun String.padSymmetric(length: Int, padChar: Char): String {
    require(length >= 0)
    if (length <= this.length) return this
    val countAtStart = (length - this.length) / 2
    return this.padStart(this.length + countAtStart, padChar).padEnd(length, padChar)
}
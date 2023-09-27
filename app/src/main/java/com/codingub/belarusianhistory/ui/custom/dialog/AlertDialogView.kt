package com.codingub.belarusianhistory.ui.custom.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.annotation.StringRes
import com.codingub.belarusianhistory.utils.ColorUtil
import com.codingub.belarusianhistory.utils.DrawableUtil
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.extension.applyScaleTouch
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.setPaddingDp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

@SuppressLint("ViewConstructor")
class AlertDialogView private constructor(builder: Builder) : FrameLayout(builder.context) {

    private val layout: LinearLayout
    private var titleTextView: TextView? = null
    private var messageTextView: TextView? = null
    private var buttonsLayout: LinearLayout? = null
    private var positiveButton: TextView? = null
    private var negativeButton: TextView? = null

    init{
        background = DrawableUtil.rect(
            color = ColorUtil.overlayColor(Resource.color(R.color.bg), 0.1f),
            corner = 27f.dp
        )

        layout = LinearLayout(context).apply { orientation = LinearLayout.VERTICAL }

        with(builder){
            title?.let {
                titleTextView = TextView(context).apply{
                    setPaddingDp(20,10,20,10)
                    setTextColor(Resource.color(R.color.text_color))
                    textSize = 9f.dp
                    typeface = Font.EXTRABOLD
                   // isSingleLine = true
                    ellipsize = TextUtils.TruncateAt.END
                    gravity = Gravity.START or Gravity.BOTTOM

                    text = it
                }
                layout.addView(
                    titleTextView, LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            }

            message?.let {
                messageTextView = TextView(context).apply {
                    setPaddingDp(20, if (title != null) 0 else 20, 20, 15)
                    setTextColor(Resource.color(R.color.text_color))
                    textSizeDp = 17f
                    typeface = Font.REGULAR
                    ellipsize = TextUtils.TruncateAt.END
                    gravity = Gravity.START

                    text = it
                }
                layout.addView(
                    messageTextView, LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            }

            if(positiveButtonText != null || negativeButtonText != null){
                buttonsLayout = LinearLayout(context).apply {
                    setPaddingDp(5, 0, 5, 0)
                    addView(
                        Space(context), LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1f
                        )
                    )
                }
                layout.addView(
                    buttonsLayout, LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        56.dp
                    )
                )
            }

            negativeButtonText?.let {
                negativeButton = createButton().apply {
//                    setTextColor(Resource.color(R.color.text2))
                    text = it
                    setOnClickListener { negativeButtonOnClick!!() }
                }
                buttonsLayout?.addView(
                    negativeButton, LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                )
            }

            positiveButtonText?.let {
                positiveButton = createButton().apply {
                    text = it
                    setOnClickListener { positiveButtonOnClick!!() }
                }
                buttonsLayout?.addView(
                    positiveButton, LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                )
            }
        }
        addView(layout)
    }

    private fun createButton(): TextView {
        return TextView(context).apply {
            setPaddingDp(15, 0, 15, 0)
            setTextColor(Resource.color(R.color.text_color))
            textSizeDp = 17f
            typeface = Font.EXTRABOLD
            isSingleLine = true
            ellipsize = TextUtils.TruncateAt.END
            gravity = Gravity.CENTER

            applyScaleTouch()
        }
    }

    class Builder(val context: Context) {

        var title: CharSequence? = null
            private set
        var message: CharSequence? = null
            private set
        var positiveButtonText: CharSequence? = null
            private set
        var positiveButtonOnClick: (() -> Unit)? = null
            private set
        var negativeButtonText: CharSequence? = null
            private set
        var negativeButtonOnClick: (() -> Unit)? = null
            private set

        fun title(title: CharSequence) = apply { this.title = title }

        fun message(message: CharSequence) = apply { this.message = message }

        fun positiveButton(text: CharSequence, onClick: () -> Unit) = apply {
            positiveButtonText = text
            positiveButtonOnClick = onClick
        }

        fun negativeButton(text: CharSequence, onClick: () -> Unit) = apply {
            negativeButtonText = text
            negativeButtonOnClick = onClick
        }

        fun title(@StringRes titleRes: Int) = title(Resource.string(titleRes))

        fun message(@StringRes messageRes: Int) = message(Resource.string(messageRes))

        fun positiveButton(@StringRes textRes: Int, onClick: () -> Unit) = positiveButton(
            Resource.string(textRes),
            onClick
        )

        fun negativeButton(@StringRes textRes: Int, onClick: () -> Unit) = negativeButton(
            Resource.string(textRes),
            onClick
        )

        fun build(): AlertDialogView = AlertDialogView(this)
    }
}

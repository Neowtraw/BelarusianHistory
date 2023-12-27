package com.codingub.belarusianhistory.ui.custom.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.codingub.belarusianhistory.databinding.ItemAlertdialogInputBinding
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource

@SuppressLint("ViewConstructor")
class AlertDialogInputView private constructor(builder: Builder) : FrameLayout(builder.context) {

    private var binding: ItemAlertdialogInputBinding

    init {
        val inflater = LayoutInflater.from(builder.context)
        binding = ItemAlertdialogInputBinding.inflate(inflater)

        with(builder) {
            title?.let {
                binding.title.typeface = Font.EXTRABOLD
                binding.title.text = it
            }
            hint?.let {
                binding.hint.hint = it
                binding.hint.typeface = Font.REGULAR
            }
            positiveButtonText?.let {
                binding.btnPositive.apply {
                    typeface = Font.REGULAR
                    text = it
                    setOnClickListener {
                        positiveButtonOnClick!!(binding.hint.text.toString())
                    }
                }
            }
            negativeButtonText?.let {
                binding.btnNegative.apply {
                    typeface = Font.REGULAR
                    text = it
                    setOnClickListener {
                        negativeButtonOnClick!!(binding.hint.text.toString())
                    }
                }
            }
        }
        addView(binding.root)
    }

    class Builder(val context: Context) {

        var title: CharSequence? = null
            private set
        var hint: CharSequence? = null
            private set
        var positiveButtonText: CharSequence? = null
            private set
        var positiveButtonOnClick: ((text: String) -> Unit)? = null
            private set
        var negativeButtonText: CharSequence? = null
            private set
        var negativeButtonOnClick: ((text: String) -> Unit)? = null
            private set

        fun title(title: CharSequence) = apply { this.title = title }

        fun hint(message: CharSequence) = apply { this.hint = hint }

        fun positiveButton(text: CharSequence, onClick: (text: String) -> Unit) = apply {
            positiveButtonText = text
            positiveButtonOnClick = onClick
        }

        fun negativeButton(text: CharSequence, onClick: (text: String) -> Unit) = apply {
            negativeButtonText = text
            negativeButtonOnClick = onClick
        }

        fun title(@StringRes titleRes: Int) = title(Resource.string(titleRes))

        fun hint(@StringRes hintRes: Int) = hint(Resource.string(hintRes))

        fun positiveButton(@StringRes textRes: Int, onClick: (text: String) -> Unit) =
            positiveButton(
                Resource.string(textRes),
                onClick
            )

        fun negativeButton(@StringRes textRes: Int, onClick: (text: String) -> Unit) =
            negativeButton(
                Resource.string(textRes),
                onClick
            )

        fun build(): AlertDialogInputView = AlertDialogInputView(this)
    }
}
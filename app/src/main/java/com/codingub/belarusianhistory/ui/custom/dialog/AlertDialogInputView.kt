package com.codingub.belarusianhistory.ui.custom.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StringRes
import com.codingub.belarusianhistory.databinding.AlertdialogInputItemBinding
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource

class AlertDialogInputView private constructor(builder: Builder) {

    private var binding: AlertdialogInputItemBinding

    init {
        val inflater = LayoutInflater.from(builder.context)
        binding = AlertdialogInputItemBinding.inflate(inflater)

        with(builder) {
            title?.let {
                binding.title.typeface = Font.EXTRABOLD
                binding.title.text = it
            }
            hint?.let {
                binding.hint.typeface = Font.REGULAR
                binding.hint.setText(it)
            }
//            positiveButtonText?.let {
//                binding.btnPositive.apply {
//                    typeface = Font.REGULAR
//                    text = it
//                    setOnClickListener {
//                        positiveButtonOnClick!!()
//                    }
//                }
//            }
//            negativeButtonText?.let {
//                binding.btnNegative.apply {
//                    typeface = Font.REGULAR
//                    text = it
//                    setOnClickListener {
//                        negativeButtonOnClick!!()
//                    }
//                }
//            }
        }
    }

    class Builder(val context: Context) {

        var title: CharSequence? = null
            private set
        var hint: CharSequence? = null
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

        fun hint(message: CharSequence) = apply { this.hint = hint }

        fun positiveButton(text: CharSequence, onClick: () -> Unit) = apply {
            positiveButtonText = text
            positiveButtonOnClick = onClick
        }

        fun negativeButton(text: CharSequence, onClick: () -> Unit) = apply {
            negativeButtonText = text
            negativeButtonOnClick = onClick
        }

        fun title(@StringRes titleRes: Int) = title(Resource.string(titleRes))

        fun hint(@StringRes hintRes: Int) = hint(Resource.string(hintRes))

        fun positiveButton(@StringRes textRes: Int, onClick: () -> Unit) = positiveButton(
            Resource.string(textRes),
            onClick
        )

        fun negativeButton(@StringRes textRes: Int, onClick: () -> Unit) = negativeButton(
            Resource.string(textRes),
            onClick
        )

        fun build(): AlertDialogInputView = AlertDialogInputView(this)
    }
}
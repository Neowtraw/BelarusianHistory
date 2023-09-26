package com.codingub.belarusianhistory.ui.custom.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.codingub.belarusianhistory.R

open class AlertDialog(context: Context) : AlertDialog(context, R.style.AlertDialog) {

    init {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
package com.codingub.belarusianhistory.ui.base

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

interface AnalyticsLogger {
    fun registerLifecyclerOwner(owner: LifecycleOwner)
}

open class AnalyticsLoggerImpl: AnalyticsLogger, LifecycleEventObserver {


    override fun registerLifecyclerOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_START -> {}
            Lifecycle.Event.ON_RESUME -> {}
            else -> {}
        }
    }
}

////////

interface DeepLinkHandler {
    fun handleDeepLink(activity: ComponentActivity, intent: Intent?)
}

class HandleDeepLinkImpl: DeepLinkHandler {
    override fun handleDeepLink(activity: ComponentActivity, intent: Intent?) {

    }
}


package com.codingub.belarusianhistory.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.TypeConverter
import com.codingub.belarusianhistory.presentation.ui.MainActivity
import com.codingub.belarusianhistory.sdk.TaskType

abstract class BaseFragment : Fragment() {
    protected val mainActivity: MainActivity get() = MainActivity.getInstance()
    override fun getContext(): Context = mainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create()
    }

    override fun onCreateView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        return createView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destroyView()
    }

    protected open fun create() {}

    protected abstract fun createView(): View

    protected open fun viewCreated() {}

    protected open fun destroyView() {}

    protected open fun observeChanges() {}


}
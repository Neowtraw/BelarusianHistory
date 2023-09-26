package com.codingub.belarusianhistory.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.sdk.UserPracticeAnswer
import com.codingub.belarusianhistory.ui.MainActivity

abstract class TaskFragment : Fragment() {

    protected val mainActivity: MainActivity get() = MainActivity.getInstance()
    override fun getContext(): Context = mainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create()
    }

    override fun onCreateView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        return createView(inf, con, state)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destroyView()
    }

    protected open fun create() {}

    protected abstract fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View

    //для получения листа resultAnswer
    abstract fun onAnswersChecked(): UserPracticeAnswer?

    protected open fun viewCreated() {}

    protected open fun destroyView() {}

    protected open fun observeChanges() {}

    protected fun pushTaskFragment(parentFragment: BaseFragment,childFragment: TaskFragment, backstack: String?) {
        mainActivity.pushChildFragment(parentFragment, childFragment, backstack, R.id.fcvTasks)
    }

}

package com.codingub.belarusianhistory.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout

class ChangeDataFragment : BaseFragment() {

    private lateinit var typeLayout: TabLayout
    private lateinit var rootLayout: LinearLayout


    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {

        createTypeLayout()
        createRootLayout()
        observeChanges()
        return rootLayout
    }

    private fun createTypeLayout() {

    }

    private fun createRootLayout() {

    }

    override fun observeChanges() {

    }
}
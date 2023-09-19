package com.codingub.belarusianhistory.presentation.ui.settings

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.setPaddingDp
import com.codingub.belarusianhistory.utils.extension.textSizeDp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment  : BaseFragment(){

    private val vm : SettingsViewModel by viewModels()

    private lateinit var rootLayout: LinearLayout
    private lateinit var themeText: TextView
    private lateinit var languageText: TextView
    private lateinit var themeListView: ThemeListView

    override fun create() {
        super.create()

        createTexts()
        createThemeList()
        createRootLayout()
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        return rootLayout
    }

    private fun createTexts(){

        themeText = TextView(requireContext()).apply {
            text = Resource.string(R.string.themes)
            setTextColor(Resource.color(R.color.text_color))
            typeface = Font.SEMIBOLD
            textSize = 10f.dp
        }

        languageText = TextView(requireContext()).apply {
            text = Resource.string(R.string.languages)
            setTextColor(Resource.color(R.color.text_color))
            textSize = 10f.dp
            typeface = Font.SEMIBOLD

        }

    }

    private fun createThemeList(){
        themeListView = ThemeListView(vm.getLastThemePos(), vm.themeList,requireContext()){
            vm.setNewTheme(it)
        }
    }

    private fun createRootLayout(){
        rootLayout = LinearLayout(requireContext()).apply {
            setBackgroundColor(Resource.color(R.color.bg))
            setPadding(16.dp)
            orientation = LinearLayout.VERTICAL

            addView(themeText, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ))

            addView(themeListView, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
           ))

            addView(languageText, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ))
        }
    }
}
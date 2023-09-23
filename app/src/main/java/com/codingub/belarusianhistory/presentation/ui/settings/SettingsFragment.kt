package com.codingub.belarusianhistory.presentation.ui.settings

import android.content.res.ColorStateList
import android.graphics.Outline
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.custom.view.SelectedView
import com.codingub.belarusianhistory.sdk.Language
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.setPaddingDp
import com.codingub.belarusianhistory.utils.extension.textSizeDp
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private val vm: SettingsViewModel by viewModels()

    private lateinit var rootLayout: LinearLayout
    private lateinit var mainText: TextView
    private lateinit var themeText: TextView
    private lateinit var languageText: TextView
    private lateinit var themeListView: ThemeListView
    private lateinit var languageView: TabLayout

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {

        createTexts()
        createThemeList()
        createLanguageView()
        createRootLayout()

        return rootLayout
    }

    private fun createTexts() {

        mainText = TextView(requireContext()).apply {
            text = Resource.string(R.string.settings)
            setTextColor(Resource.color(R.color.text_color))
            typeface = Font.EXTRABOLD
            textSize = 12f.dp
        }

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

    private fun createThemeList() {
        themeListView = ThemeListView(vm.getLastThemePos(), vm.themeList, requireContext()) {
            vm.setNewTheme(it)
            //анимация для темы

        }
    }


    private fun createLanguageView() {
        languageView = TabLayout(requireContext()).apply {
            setBackgroundResource(Resource.drawable("item_rounded"))
            val background = this.background as GradientDrawable
            background.color = ColorStateList.valueOf(Resource.color(R.color.bg_btn))
            tabMode = TabLayout.MODE_AUTO
            overScrollMode = View.OVER_SCROLL_NEVER
            setSelectedTabIndicator(null)
            tabRippleColor = null
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRect(0, 5.dp, view.width, view.height)
                }
            }

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val language = tab.customView as SelectedView
                    language.setChecked(true, animated = true)
                    //тут будет логика на выбор языка через vm
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    (tab.customView as SelectedView).setChecked(false, animated = true)
                }

                override fun onTabReselected(tab: TabLayout.Tab) = Unit
            })
        }

        val languages = Language.values()
        languages.forEachIndexed { index, language ->
            languageView.apply {
                newTab().apply {
                    customView = SelectedView(requireContext()).apply {
                        text = Resource.string(language.nameRes)
                    }
                    view.setPaddingDp(
                        if (index == 0) 16 else 4,
                        0,
                        if (index == languages.lastIndex) 16 else 4,
                        0
                    )
                }.also {
                    addTab(it)
                }
            }
        }
    }


    private fun createRootLayout() {
        rootLayout = LinearLayout(requireContext()).apply {
            setBackgroundColor(Resource.color(R.color.bg))
            setPadding(16.dp)
            orientation = LinearLayout.VERTICAL

            addView(
                mainText, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
            mainText.setPadding(0,0,0,16.dp)

            addView(
                themeText, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

            addView(
                themeListView, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

            addView(
                languageText, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

            addView(
                languageView, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
    }
}
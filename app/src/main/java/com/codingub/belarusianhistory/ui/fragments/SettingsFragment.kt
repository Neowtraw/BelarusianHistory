package com.codingub.belarusianhistory.ui.fragments

import android.content.res.ColorStateList
import android.graphics.Outline
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.local.prefs.ApplicationConfig
import com.codingub.belarusianhistory.databinding.FragmentSettingsBinding
import com.codingub.belarusianhistory.sdk.Language
import com.codingub.belarusianhistory.ui.MainActivity
import com.codingub.belarusianhistory.ui.adapters.ThemeAdapter
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.custom.view.SelectedView
import com.codingub.belarusianhistory.ui.viewmodels.SettingsViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.setPaddingDp
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private val vm: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentSettingsBinding

    private lateinit var themeAdapter: ThemeAdapter
    private val tabs: MutableList<TabLayout.Tab> = mutableListOf()

    private val languages: Array<Language> = Language.values()

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inf, con, false)

        createTexts()
        createThemeList()
        createLanguageView()

        return binding.root
    }

    override fun viewCreated() {
        super.viewCreated()

        setupListeners()
    }

    private fun createTexts() {
        binding.tvHeader.typeface = Font.EXTRABOLD
        binding.tvLanguages.typeface = Font.EXTRABOLD
        binding.tvThemes.typeface = Font.EXTRABOLD
        binding.btnResetAchieves.typeface = Font.REGULAR
        binding.btnResetPractice.typeface = Font.REGULAR
        binding.btnResetTickets.typeface = Font.REGULAR

    }

    private fun createThemeList() {
        binding.rvThemes.apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(context)
            themeAdapter = ThemeAdapter(vm.themeList, vm.getLastThemePos()) {
                vm.setNewTheme(it)
            }
            adapter = themeAdapter
        }
    }

    private fun createLanguageView() {
        binding.tlLanguages.apply {
            setBackgroundResource(Resource.drawable("item_rounded_settings"))
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
                    val index = tabs.indexOf(tab)
                    if (index == -1) return

//                    val languageView = tab.customView as SelectedView
//                    languageView.setChecked(true, animated = true)

                    val language = languages[index]
                    if (language.code != ApplicationConfig.getLanguage().code) {
                        vm.setNewLanguage(language)
                        MainActivity.getInstance().recreate()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    (tab.customView as SelectedView).setChecked(false, animated = true)
                }

                override fun onTabReselected(tab: TabLayout.Tab) = Unit
            })
        }

        languages.forEachIndexed { index, language ->
            binding.tlLanguages.apply {
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
                    tabs.add(it)
                }
            }
        }

        binding.tlLanguages.apply {
            val tab = tabs[languages.indexOf(ApplicationConfig.getLanguage())]
            selectTab(tab)
            (tab.customView as? SelectedView)?.setChecked(true, animated = true)
        }
    }

    private fun setupListeners() {


    }
}
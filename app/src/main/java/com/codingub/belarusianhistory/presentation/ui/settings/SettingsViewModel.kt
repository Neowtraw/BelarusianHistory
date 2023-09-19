package com.codingub.belarusianhistory.presentation.ui.settings

import androidx.lifecycle.ViewModel
import com.codingub.belarusianhistory.domain.use_case.shared.GetSavedTheme
import com.codingub.belarusianhistory.domain.use_case.shared.SetTheme
import com.codingub.belarusianhistory.sdk.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSavedTheme: GetSavedTheme,
    private val setTheme: SetTheme
) : ViewModel() {

    var themeList: List<ThemeType> = ThemeType.getAllThemes()

    fun getLastThemePos() : Int{
        return getSavedTheme.invoke().id
    }

    fun setNewTheme(theme: ThemeType){
        return setTheme(theme)
    }
}
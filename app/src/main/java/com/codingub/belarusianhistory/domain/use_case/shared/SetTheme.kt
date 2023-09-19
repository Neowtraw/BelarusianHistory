package com.codingub.belarusianhistory.domain.use_case.shared

import androidx.appcompat.app.AppCompatDelegate
import com.codingub.belarusianhistory.App
import com.codingub.belarusianhistory.domain.repository.ConfigSharedRepository
import com.codingub.belarusianhistory.presentation.ui.MainActivity
import com.codingub.belarusianhistory.sdk.ThemeType
import javax.inject.Inject

class SetTheme @Inject constructor(
   private val repository: ConfigSharedRepository
) {

    operator fun invoke(theme: ThemeType){
        repository.setSavedTheme(theme)

        //нужно изменить с MainActivity !!! setTheme()
        AppCompatDelegate.setDefaultNightMode(theme.nightMode)
        MainActivity.getInstance().recreate()
    }
}
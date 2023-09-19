package com.codingub.belarusianhistory.domain.use_case.shared

import com.codingub.belarusianhistory.domain.repository.ConfigSharedRepository
import com.codingub.belarusianhistory.sdk.ThemeType
import javax.inject.Inject

class GetSavedTheme @Inject constructor(
    private val repository: ConfigSharedRepository
) {

    operator fun invoke() : ThemeType{
        return repository.getSavedThemeType()
    }
}
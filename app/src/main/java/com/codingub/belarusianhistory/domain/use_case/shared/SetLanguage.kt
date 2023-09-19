package com.codingub.belarusianhistory.domain.use_case.shared

import com.codingub.belarusianhistory.domain.repository.ConfigSharedRepository
import com.codingub.belarusianhistory.sdk.Language
import javax.inject.Inject

class SetLanguage @Inject constructor(
    private val repository: ConfigSharedRepository
) {

    operator fun invoke() : Language{
        return repository.getSavedLanguage()
    }
}
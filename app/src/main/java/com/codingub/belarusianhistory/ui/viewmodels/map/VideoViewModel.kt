package com.codingub.belarusianhistory.ui.viewmodels.map

import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(): ViewModel() {

    private val _uri: MutableStateFlow<String?> = MutableStateFlow(null)
    val uri = _uri.asStateFlow()

    fun setUri(newUri: String) { _uri.value = newUri }
}
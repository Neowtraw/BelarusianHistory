package com.codingub.belarusianhistory.ui.viewmodels.change


sealed class InsertedUiState {
    data object Inserted : InsertedUiState()
    data object Loading : InsertedUiState()
    data class Failed(val error: String) : InsertedUiState()
}

sealed class DeletedUiState {
    data object Deleted : DeletedUiState()
    data object Loading : DeletedUiState()
    data class Failed(val error: String) : DeletedUiState()
}



package com.codingub.belarusianhistory.ui.auth

import com.codingub.belarusianhistory.data.repository.users.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    repository: UserRepository
) {



}
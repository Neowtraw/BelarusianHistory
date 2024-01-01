package com.codingub.belarusianhistory.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingub.belarusianhistory.data.local.prefs.UserConfig
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.domain.use_cases.GetTypeResultsUseCase
import com.codingub.belarusianhistory.domain.use_cases.GetTypeAchievesUseCase
import com.codingub.belarusianhistory.sdk.AchieveType
import com.codingub.belarusianhistory.sdk.models.achieves.AchieveDto
import com.codingub.belarusianhistory.sdk.models.userdata.ResultDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchieveViewModel @Inject constructor(
    private val getTypeAchievesUseCase: GetTypeAchievesUseCase,
    private val getAllResultsUseCase: GetTypeResultsUseCase
) : ViewModel() {

    private val _data: MutableStateFlow<Pair<ServerResponse<List<AchieveDto>>, ServerResponse<List<ResultDto>>>> =
        MutableStateFlow(Pair(ServerResponse.Loading(), ServerResponse.Loading()))
    val data = _data.asStateFlow()

    private val _articles: MutableStateFlow<List<AchieveStateUi>> =
        MutableStateFlow(emptyList())
    val articles = _articles.asStateFlow()

    fun updateAchieves(type: AchieveType) {
        viewModelScope.launch(Dispatchers.IO) {
            val achievesDeferred = async { getTypeAchievesUseCase(type) }
            val userResultsDeferred = async {
                getAllResultsUseCase(
                    userLogin = UserConfig.getLogin(),
                    type = type
                )
            }

            val achieves = achievesDeferred.await()
            val userResults = userResultsDeferred.await()

            _data.value = Pair(achieves, userResults)
        }
    }

    fun getAchievesType() {

        data.value.second.data?.let{ results ->
            val ids = results.map { it.id }

            _articles.value = data.value.first.data!!.map {
                AchieveStateUi(
                    achieve = it,
                    isPassed = ids.contains(it.id)
                )
            }.sortedByDescending { it.isPassed }
        }

    }

}

// showing list
data class AchieveStateUi(
    val achieve: AchieveDto,
    val isPassed: Boolean
)
package com.example.toptracercase.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toptracercase.giphy.GiphyRepository
import com.example.toptracercase.login.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(
        WelcomeViewState(
            username = sessionRepository.currentUser.value?.username,
            giphy = GiphyResult.Loading
        )
    )

    val viewState = _viewState

    init {
        viewModelScope.launch {
            giphyRepository.getRandomGiphy()
                .onSuccess {
                    _viewState.value = _viewState.value.copy(giphy = GiphyResult.Success(it))
                }
                .onFailure {
                    _viewState.value = _viewState.value.copy(giphy = GiphyResult.Error)
                }
        }
    }
}

data class WelcomeViewState(val username: String?, val giphy: GiphyResult)

sealed class GiphyResult {
    data class Success(val url: String) : GiphyResult()
    object Loading : GiphyResult()
    object Error : GiphyResult()
}
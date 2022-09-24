package com.example.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
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
                .onSuccess { result ->
                    emitResult(GiphyResult.Success(result))
                }
                .onFailure {
                    emitResult(GiphyResult.Error)
                }
        }
    }

    fun logout() {
        sessionRepository.logout()
    }

    private fun emitResult(result: GiphyResult) {
        _viewState.update { it.copy(giphy = result) }
    }
}

data class WelcomeViewState(val username: String?, val giphy: GiphyResult)

sealed class GiphyResult {
    data class Success(val giphy: Giphy) : GiphyResult()
    object Loading : GiphyResult()
    object Error : GiphyResult()
}
package com.example.welcome

import com.example.network.ApiResult
import com.example.network.GiphyApiService
import com.example.network.GiphyResponse
import javax.inject.Inject
import javax.inject.Singleton

private const val FALLBACK_USERNAME = "Unknown"
private const val FALLBACK_TITLE = "Untitled"
private const val BY_DELIMITER = " by "

@Singleton
class GiphyRepository @Inject constructor(
    private val apiService: GiphyApiService
) {
    suspend fun getRandomGiphy(): Result<Giphy> =
        when (val result = apiService.getRandomGif()) {
            is ApiResult.Error -> Result.failure(result.exception)
            is ApiResult.Success -> Result.success(result.data.toGiphy())
        }
}

private fun GiphyResponse.toGiphy() = with(data) {
    Giphy(
        title = title.cleanTitle().ifBlank { FALLBACK_TITLE },
        username = user?.displayName ?: FALLBACK_USERNAME,
        url = images.image.url
    )
}

private fun String.cleanTitle() =
    replaceAfter(delimiter = BY_DELIMITER, replacement = "")
        .replace(oldValue = BY_DELIMITER, newValue = "")
        .trim()

data class Giphy(val title: String, val username: String, val url: String)
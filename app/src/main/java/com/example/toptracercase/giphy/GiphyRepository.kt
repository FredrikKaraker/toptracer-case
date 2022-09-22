package com.example.toptracercase.giphy

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepository @Inject constructor() {

    suspend fun getRandomGiphy(): Result<String> {
        delay(300_000)
        return Result.success("")
    }
}
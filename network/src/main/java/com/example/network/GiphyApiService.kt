package com.example.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

private const val API_KEY = "tHrsHKt0c1lq7AHjsCBNSDaJUAcHI5P5"

interface GiphyApi {
    @GET("gifs/random?api_key=$API_KEY")
    suspend fun getGif(): Response<GiphyResponse>
}

@JsonClass(generateAdapter = true)
data class GiphyResponse(val data: Data) {
    @JsonClass(generateAdapter = true)
    data class Data(val title: String, val images: Images, val user: User? = null)

    @JsonClass(generateAdapter = true)
    data class User(@Json(name = "display_name") val displayName: String? = null)

    @JsonClass(generateAdapter = true)
    data class Images(@Json(name = "downsized_large") val image: Image) {

        @JsonClass(generateAdapter = true)
        data class Image(val url: String)
    }
}

class GiphyApiService @Inject constructor(
    @GiphyRetrofit private val retrofit: Retrofit
) {
    private val api by lazy { retrofit.create(GiphyApi::class.java) }

    suspend fun getRandomGif(): ApiResult<GiphyResponse> = wrapInApiResult { api.getGif() }
}
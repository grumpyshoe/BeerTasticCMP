package com.grumpyshoe.beertasticcmp.data.source.network

import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiError
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiResult
import com.grumpyshoe.beertasticcmp.domain.beer.utils.ApiSuccess
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess


suspend inline fun <reified T> handleApi(execute: () -> HttpResponse): ApiResult<T> = try {
    val response = execute()
    val body = response.body<T>()
    if (response.status.isSuccess()) {
        ApiSuccess(body ?: Unit as T)
    } else {
        try {
//            val errorBody = response.bodyAsText()
            val code =
                response.status.value//JsonObject(errorBody).getJSONObject("error").getInt("code")
            ApiError("ErrorCode = $code")
        } catch (e: Exception) {
            ApiError(e.message)
        }
    }
} catch (e: Exception) {
    ApiError(e.message)
}

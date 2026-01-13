package com.grumpyshoe.beertasticcmp.data.source.network.beer.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class PunkAPI {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getBeers(page: Int): HttpResponse {

        val response = client.get("${BASE_URL}/beers?page=$page&per_page=80")
        return response
    }

    suspend fun getBeerById(beerId: Int): HttpResponse {

        val response = client.get("${BASE_URL}/beers/$beerId")
        return response
    }

    suspend fun getRandomBeer(): HttpResponse {

        val response = client.get("${BASE_URL}/beers/random")
        return response
    }

    companion object {
        const val BASE_URL = "https://punkapi-alxiw.amvera.io/v3"
    }
}

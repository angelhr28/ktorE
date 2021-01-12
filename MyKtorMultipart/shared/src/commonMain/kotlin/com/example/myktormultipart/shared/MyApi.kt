package com.example.myktormultipart.shared

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*

class MyApi {
    val httpClient = HttpClient() {
        expectSuccess = false
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                isLenient = true
                ignoreUnknownKeys = true
            }
            serializer = KotlinxSerializer(json)
        }

        install(ResponseObserver) {
            onResponse { response ->
                println("HTTP status: ${response.status.value}")
                println("""
                ${response.call.response.content.readUTF8Line()}
                """".trimIndent())
            }
        }

        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                when (response.status.value) {
                    in 300..399 -> throw RedirectResponseException(response)
                    in 400..499 -> throw ClientRequestException(response)
                    in 500..599 -> throw ServerResponseException(response)
                }
                if (response.status.value >= 600) {
                    throw ResponseException(response)
                }
            }

            handleResponseException {cause ->
                throw cause
            }
        }
    }

    suspend inline fun postServiceMultipart() {
        return httpClient.post("http://192.168.1.13:3001/api/publicarNoticia") {  // CAMBIA TU CLAVE IP SEGUN SEA EL CASO
            body = MultiPartFormDataContent(
                formData {
                    append("key", "value")
                }
            )
        }
    }

}
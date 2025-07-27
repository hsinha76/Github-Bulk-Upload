package com.hsdroid.bulkrepouploadforgithub.data

import com.hsdroid.bulkrepouploadforgithub.data.Endpoints.repoCreation
import com.hsdroid.bulkrepouploadforgithub.data.Endpoints.verifyGithubToken
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import java.util.Base64

@Serializable
data class GitHubRepoRequest(val name: String, val private: Boolean = true)

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun createGitHubRepoKtor(
    repoName: String,
    username: String,
    token: String,
    isPrivate: Boolean
): Boolean {
    return try {
        val response = client.post(repoCreation) {
            contentType(ContentType.Application.Json)
            headers {
                append(
                    HttpHeaders.Authorization, "Basic " + Base64.getEncoder()
                        .encodeToString("$username:$token".toByteArray())
                )
            }
            setBody(GitHubRepoRequest(repoName, isPrivate))
        }

        response.status.value in 200..299
    } catch (e: Exception) {
        false
    }
}

suspend fun followDeveloper(username: String, token: String): Boolean {
    return try {
        val response = client.put("https://api.github.com/user/following/$username") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
                append(HttpHeaders.Accept, "application/vnd.github+json")
            }
        }

        response.status.value == 204
    } catch (e: Exception) {
        false
    }
}

suspend fun verifyGitHubTokenScopes(token: String): Boolean {
    return try {
        val response: HttpResponse = client.get(verifyGithubToken) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
                append(HttpHeaders.Accept, "application/vnd.github+json")
            }
        }

        val scopesHeader = response.headers["X-OAuth-Scopes"] ?: ""
        val scopes = scopesHeader.split(",").map { it.trim() }
        val requiredScopes = listOf("repo", "workflow", "user:follow") //to remove
        val hasAll = requiredScopes.all { it in scopes }
        hasAll
    } catch (e: Exception) {
        false
    }
}

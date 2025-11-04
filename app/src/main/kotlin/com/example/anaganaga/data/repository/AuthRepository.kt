package com.example.anaganaga.data.repository

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.example.anaganaga.LoginMutation

// Result class for login
data class LoginResult(
    val success: Boolean,
    val token: String?,
    val errors: List<String>
)

class AuthRepository(private val apolloClient: ApolloClient) {
    suspend fun login(email: String, password: String): LoginResult {
        return try {
            val response = apolloClient.mutation(
                LoginMutation(email = email, password = password)
            ).execute()
            val token = response.data?.login?.token
            val errors = response.data?.login?.errors ?: emptyList()
            LoginResult(
                success = token != null && token.isNotBlank() && errors.isEmpty(),
                token = token,
                errors = errors
            )
        } catch (e: ApolloException) {
            Log.e("AuthRepository", "ApolloException: ${e.message}", e)
            LoginResult(success = false, token = null, errors = listOf(e.message ?: "Unknown error"))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Network/Other Exception: ${e.message}", e)
            LoginResult(success = false, token = null, errors = listOf(e.message ?: "Unknown error"))
        }
    }
}

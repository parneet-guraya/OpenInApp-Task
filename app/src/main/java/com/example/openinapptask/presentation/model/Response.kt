package com.example.openinapptask.model

sealed class Response<out T> {
    object Loading : Response<Nothing>()
    data class Success<T>(val result: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
}
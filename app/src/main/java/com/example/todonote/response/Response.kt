package com.example.todonote.response

import com.example.todonote.presentation.HomeState

sealed class Response {
    data class Success(val data: HomeState) : Response()
    data class Error(val message: String) : Response()
    data object Loading : Response()
}
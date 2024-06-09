package com.example.todonote.navigation

sealed class Destination(val route: String) {

    data object Home : Destination("home_screen")
    data object Details : Destination("details_screen")

}
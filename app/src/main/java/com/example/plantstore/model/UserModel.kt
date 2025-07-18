package com.example.plantstore.model

data class UserModel(
    var userId: String = "",
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var gender: String = "",
    val phone: String="",
    var address: String = "",
)

package ru.alex.minibank.model

import java.io.Serializable

data class User(val name: String, val token: String, val balance: Long) : Serializable

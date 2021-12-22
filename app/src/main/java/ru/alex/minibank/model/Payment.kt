package ru.alex.minibank.model

data class Payment(val id: Long, val sourceName: String, val targetName: String, val amount: Long)

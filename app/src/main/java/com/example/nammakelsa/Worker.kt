package com.example.nammakelsa

data class Worker(
    val id: String = "",
    val name: String = "",
    val skill: String = "",
    val daily_rate: String = "",
    val location: String = "",
    val phone: String = "",
    val is_available: Boolean = false
)
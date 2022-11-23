package com.example.myapplication.data.models

data class MonsterEntry(
    val category: String? = "",
    val common_locations: List<String>? = listOf(""),
    val description: String? = "",
    val drops: List<String>? = listOf(""),
    val id: Int? = 0,
    val image: String? = "",
    val name: String? = ""
)
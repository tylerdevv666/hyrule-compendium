package com.example.myapplication.data.models

data class Entry(
    val category: String,
    val common_locations: List<String>,
    val description: String,
    val drops: List<Any>,
    val id: Int,
    val image: String,
    val name: String
)
package com.example.myapplication.utilities

import com.example.myapplication.data.models.Entry
import com.example.myapplication.data.models.MonsterEntry

const val BASE_URL = "https://botw-compendium.herokuapp.com/api/v2/"

val BLANK_MONSTER_LIST = listOf(
    MonsterEntry(
        category = "",
        common_locations = listOf(""),
        description = "",
        drops = listOf(""),
        id = 0,
        image = "https://i.kym-cdn.com/entries/icons/original/000/001/368/cover6.jpg",
        name = "Unknown"
    )
)

val BLANK_ENTRY = Entry(
    category = "",
    common_locations = listOf(""),
    description = "",
    drops = listOf(""),
    id = 0,
    image = "https://i.kym-cdn.com/entries/icons/original/000/001/368/cover6.jpg",
    name = "Unknown"
)
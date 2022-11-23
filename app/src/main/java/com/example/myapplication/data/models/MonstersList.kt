package com.example.myapplication.data.models

data class MonstersList(
    // Maintain `data` val name or app will not pull api data correctly
    val `data`: List<MonsterEntry>
)
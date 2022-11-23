package com.example.myapplication.data.models

data class EntryList(
    // Maintain `data` val name or app will not pull api data correctly
    val `data`: Entry
)
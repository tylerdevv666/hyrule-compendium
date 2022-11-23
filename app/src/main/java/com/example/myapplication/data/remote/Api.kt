package com.example.myapplication.data.remote

import com.example.myapplication.data.models.EntryList
import com.example.myapplication.data.models.MonstersList
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("category/monsters")
    suspend fun getMonsterData(): MonstersList

    @GET("entry/{id}")
    suspend fun getEntry(
        @Path("id") id: Int
    ): EntryList
}
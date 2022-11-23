package com.example.myapplication.repo

import com.example.myapplication.data.remote.Api
import com.example.myapplication.data.models.EntryList
import com.example.myapplication.data.models.MonstersList
import com.example.myapplication.utilities.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class Repository  @Inject constructor(
    private val api: Api
) {
    suspend fun getMonsterData(): Resource<MonstersList> {
        val response = try {
            api.getMonsterData()
        } catch (e: Exception) {
            return  Resource.Error("Unable to retrieve monster data from remote database")
        }
        return Resource.Success(response)
    }

    suspend fun getEntry(id: Int): Resource<EntryList> {
        val response = try {
            api.getEntry(id)
        } catch (e: Exception) {
            return  Resource.Error("Unable to retrieve entry data from remote database")
        }
        return Resource.Success(response)
    }
}
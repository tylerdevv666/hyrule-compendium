package com.example.myapplication.ui.screens.compendium

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.MonsterEntry
import com.example.myapplication.repo.Repository
import com.example.myapplication.utilities.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompendiumViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    var loading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var monstersList = mutableStateOf<List<MonsterEntry>>(listOf())

    init {
        fetchMonsterData()
    }

    fun fetchMonsterData() {
        viewModelScope.launch {
            loading.value = true
            when (val result = repository.getMonsterData()) {
                is Resource.Success -> {
                    val monstersEntries = result.data?.data?.mapIndexed { _, entry ->
                        MonsterEntry(
                            category = entry.category,
                            common_locations = entry.common_locations,
                            description = entry.description,
                            drops = entry.drops,
                            id = entry.id,
                            image = entry.image,
                            name = entry.name
                        )
                    } ?: BLANK_MONSTER_LIST
                    loading.value = false
                    errorMessage.value = ""
                    monstersList.value += monstersEntries.sortedBy { it.id }
                }
                is Resource.Error -> {
                    loading.value = false
                    errorMessage.value = result.message ?: "An error occurred"
                }
                else -> {
                    loading.value = false
                    errorMessage.value = "An unknown error occurred"
                }
            }
        }
    }
}
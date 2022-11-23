package com.example.myapplication.ui.screens.entrydetail

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.models.EntryList
import com.example.myapplication.repo.Repository
import com.example.myapplication.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntryDetailViewModel @Inject constructor(
    private val repository: Repository,
): ViewModel() {
    suspend fun fetchEntryData(entryId: Int): Resource<EntryList> {
        return repository.getEntry(entryId)
    }
}
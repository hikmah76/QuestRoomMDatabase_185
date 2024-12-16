package com.example.room10.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.initializer
import com.example.room10.KrsApp

import com.example.roomlocaldb1.ui.viewmodel.DetailMhsViewModel
import com.example.roomlocaldb1.ui.viewmodel.HomeMhsViewModel
import com.example.roomlocaldb1.ui.viewmodel.MahasiswaViewModel
import com.example.roomlocaldb1.ui.viewmodel.UpdateMhsViewModel

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            MahasiswaViewModel(
                krsApp().containerApp.repositoryMhs
            )
        }
        initializer {
            HomeMhsViewModel (
                krsApp().containerApp.repositoryMhs
            )
        }
        initializer {
            DetailMhsViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMhs,
            )
        }
        initializer {
            UpdateMhsViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMhs,
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)
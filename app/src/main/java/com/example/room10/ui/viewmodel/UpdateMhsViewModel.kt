package com.example.roomlocaldb1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room10.data.entity.Mahasiswa
import com.example.room10.repository.RepositoryMhs
import com.example.room10.ui.navigation.DestinasiUpdate

import com.example.roomlocaldb1.ui.viewmodel.MahasiswaViewModel.MhsUIState
import com.example.roomlocaldb1.ui.viewmodel.MahasiswaViewModel.MahasiswaEvent
import com.example.roomlocaldb1.ui.viewmodel.MahasiswaViewModel.FormErrorState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMhsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {
    var updateUiState by mutableStateOf(MhsUIState())
        private set
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    init {
        viewModelScope.launch {
            updateUiState = repositoryMhs.getMhs(_nim)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    fun updateState(mahasiswaEvent: MahasiswaEvent) { //Memperbarui status UI
        updateUiState = updateUiState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    fun validateFields(): Boolean { //Validasi form, untuk memastikan semua field terisi dengan benar
        val event = updateUiState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong")
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() { // Untuk memperbarui data mahasiswa dan menampilkan pesan
        val currentEvent = updateUiState.mahasiswaEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.updateMhs(currentEvent.toMahasiswaEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        mahasiswaEvent = MahasiswaEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMesssage diatur: ${updateUiState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(snackBarMessage = "Data gagal diupdate")
        }
    }
    fun resetSnackBarMessage() {
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
}

fun Mahasiswa.toUIStateMhs(): MhsUIState = MhsUIState (
    mahasiswaEvent = this.toDetailUiEvent(),
)
package com.example.room10.repository

import com.example.room10.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun inssertMhs(mahasiswa: Mahasiswa)
    //getAllMhs
    fun getAllMhs(): Flow<List<Mahasiswa>>

    //getMhs
    fun getMhs(nim: String): Flow<Mahasiswa>

    //deleteMhs
    suspend fun deleteMhs(mahasiswa: Mahasiswa)

    //updateMhs
    suspend fun updateMhs(mahasiswa: Mahasiswa)


}
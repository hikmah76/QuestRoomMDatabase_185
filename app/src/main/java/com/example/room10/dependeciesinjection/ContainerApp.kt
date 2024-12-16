package com.example.room10.dependeciesinjection

import android.content.Context
import com.example.room10.data.database.KrsDatabase
import com.example.room10.repository.LocalRepositoryMhs
import com.example.room10.repository.RepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val  repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}
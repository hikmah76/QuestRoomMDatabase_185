package com.example.room10

import android.app.Application
import com.example.room10.dependeciesinjection.ContainerApp


class KrsApp: Application() { //Membuat objek aplikasi yang bisa digunakan di seluruh aplikasi
    lateinit var containerApp: ContainerApp // Fungsi untuk menyimpan

    override fun onCreate(){
        super.onCreate()
        containerApp = ContainerApp(this)

    }
}
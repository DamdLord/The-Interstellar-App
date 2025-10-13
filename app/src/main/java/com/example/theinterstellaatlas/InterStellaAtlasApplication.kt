package com.example.theinterstellaatlas

import android.app.Application

class InterStellaAtlasApplication:Application (){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
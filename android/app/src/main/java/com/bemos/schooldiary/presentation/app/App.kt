package com.bemos.schooldiary.presentation.app

import android.app.Application
import com.bemos.schooldiary.presentation.di.app_component.AppComponent
import com.bemos.schooldiary.presentation.di.app_component.DaggerAppComponent
import com.bemos.schooldiary.presentation.di.app_component.appComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory()
            .create(this)
    }
}
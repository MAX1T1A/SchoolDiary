package com.bemos.schooldiary.presentation.di.app_component

import android.content.Context
import com.bemos.schooldiary.data.di.modules.DataModule
import com.bemos.schooldiary.data.di.modules.NetworkModule
import com.bemos.schooldiary.presentation.app.App
import com.bemos.schooldiary.presentation.di.AppModule
import com.bemos.schooldiary.presentation.main_activity.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context
        ): AppComponent
    }
}

val Context.appComponent : AppComponent get() {
    return if (this is App) {
        appComponent
    } else {
        applicationContext.appComponent
    }
}
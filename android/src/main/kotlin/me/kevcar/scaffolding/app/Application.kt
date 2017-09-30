package me.kevcar.scaffolding.app

import android.app.Application
import me.kevcar.scaffolding.di.component.AppComponent
import me.kevcar.scaffolding.di.component.DaggerAppComponent

class Application : Application() {

    val appComponent: AppComponent = DaggerAppComponent.builder().build()
}

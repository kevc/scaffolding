package me.kevcar.scaffolding.app

import android.app.Application
import android.content.Context
import me.kevcar.scaffolding.di.component.AppComponent
import me.kevcar.scaffolding.di.component.DaggerAppComponent
import me.kevcar.scaffolding.di.module.AppModule

class Application : Application() {

    val appComponent: AppComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()

    companion object {


        fun getComponent(context: Context): AppComponent {
            return (context.applicationContext as me.kevcar.scaffolding.app.Application)
                    .appComponent
        }
    }
}

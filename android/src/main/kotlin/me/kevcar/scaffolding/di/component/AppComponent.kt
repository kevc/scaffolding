package me.kevcar.scaffolding.di.component

import dagger.Component
import me.kevcar.scaffolding.core.di.AppScope
import me.kevcar.scaffolding.di.module.AppModule

@AppScope
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

}

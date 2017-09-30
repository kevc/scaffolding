package me.kevcar.scaffolding.di.component

import dagger.Component
import me.kevcar.scaffolding.core.di.AppScope
import me.kevcar.scaffolding.di.module.AppModule
import me.kevcar.scaffolding.presentation.model.AppModel
import me.kevcar.scaffolding.presentation.model.Epic

@AppScope
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun appModel(): AppModel
}

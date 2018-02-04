package me.tatocaster.revoluttest

import android.content.Context
import android.content.res.Resources
import dagger.Component
import me.tatocaster.revoluttest.data.DataComponent
import me.tatocaster.revoluttest.data.DataModule
import java.util.*
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (DataModule::class)])
interface AppComponent : DataComponent {

    fun inject(app: App)

    // expose dependencies to scope graph
    fun exposeAppContext(): Context

    fun exposeResources(): Resources

    fun exposeLocale(): Locale

}
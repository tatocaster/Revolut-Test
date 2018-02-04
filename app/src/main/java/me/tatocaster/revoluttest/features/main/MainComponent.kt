package me.tatocaster.revoluttest.features.main

import dagger.Component
import me.tatocaster.revoluttest.AppComponent
import me.tatocaster.revoluttest.di.scope.ActivityScope

@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(view: MainActivity)

}
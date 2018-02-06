package me.tatocaster.revoluttest.features.main

import dagger.Component
import me.tatocaster.revoluttest.AppComponent
import me.tatocaster.revoluttest.di.scope.ActivityScope
import me.tatocaster.revoluttest.features.main.usecase.RatesListUseCaseModule

@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = [(MainModule::class), (RatesListUseCaseModule::class)])
interface MainComponent {
    fun inject(view: MainActivity)

}
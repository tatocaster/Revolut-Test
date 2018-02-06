package me.tatocaster.revoluttest.features.main.usecase

import dagger.Module
import dagger.Provides

@Module
class RatesListUseCaseModule {
    @Provides
    fun provideGetAllAvailableProviders(usecase: RatesListRepositoryImpl): RatesListRepository = usecase
}
package com.rosorio.mindicador.di.modules

import com.rosorio.mindicador.api.MindicadorApi
import com.rosorio.mindicador.datasources.LoginDataSource
import com.rosorio.mindicador.view.commons.viewmodels.ViewModelFactory
import com.rosorio.mindicador.view.main.MainActivity
import com.rosorio.mindicador.view.main.MainInteractor
import com.rosorio.mindicador.view.main.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Provider

@Module(
    includes = [
        DataSourceModule::class
    ]
)
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun contribureMainActivity(): MainActivity

    companion object {
        @JvmStatic
        @Provides
        fun provideViewModel(interactor: MainInteractor) = MainViewModel(interactor)

        @JvmStatic
        @Provides
        fun provideInteractor(api: MindicadorApi, datasource: LoginDataSource) = MainInteractor(api,datasource)

        @JvmStatic
        @Provides
        fun providesViewModelFactory(provider: Provider<MainViewModel>): ViewModelFactory<MainViewModel> =
            ViewModelFactory(provider)
    }
}

package com.rosorio.mindicador.di.modules

import android.content.SharedPreferences
import com.rosorio.mindicador.view.commons.viewmodels.ViewModelFactory
import com.rosorio.mindicador.datasources.LoginDataSource
import com.rosorio.mindicador.datasources.PreferencesLoginDataSources
import com.rosorio.mindicador.view.login.LoginActivity
import com.rosorio.mindicador.view.login.LoginInteractor
import com.rosorio.mindicador.view.login.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named
import javax.inject.Provider

@Module(
    includes = [
        DataSourceModule::class
    ]
)
abstract class LoginModule {
    @ContributesAndroidInjector
    abstract fun contributeLogin(): LoginActivity

    companion object {
        @JvmStatic
        @Provides
        fun provideViewModel(interactor: LoginInteractor) = LoginViewModel(interactor)

        @JvmStatic
        @Provides
        fun provideInteractor(dataSource: LoginDataSource) = LoginInteractor(dataSource)

        @JvmStatic
        @Provides
        fun providesViewModelFactory(provider: Provider<LoginViewModel>): ViewModelFactory<LoginViewModel> =
            ViewModelFactory(provider)
    }

}
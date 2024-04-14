package com.fadlurahmanf.monorepo.app_example.others.di

import com.fadlurahmanf.monorepo.app_example.presentation.features.ListFeatureActivity
import com.fadlurahmanf.monorepo.app_example.presentation.features.crypto.ExampleCryptoActivity
import dagger.Component

@Component(modules = [AppExampleModule::class])
interface AppExampleComponent {
    @Component.Factory
    interface Factory {
        fun create(): AppExampleComponent
    }

    fun inject(activity: ListFeatureActivity)
    fun inject(activity: ExampleCryptoActivity)
}
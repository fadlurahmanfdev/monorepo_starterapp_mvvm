package com.fadlurahmanf.monorepo.app_example

import com.fadlurahmanf.monorepo.app_example.presentation.features.ListFeatureActivity
import com.fadlurahmanf.monorepo.app_example.presentation.features.crypto.ExampleCryptoActivity
import com.fadlurahmanf.monorepo.app_example.presentation.features.notification.ExampleNotificationActivity
import com.fadlurahmanf.monorepo.app_notification.others.di.AppNotificationModule
import dagger.Component

@Component(
    modules = [
        AppNotificationModule::class,
        AppExampleModule::class,
    ]
)
interface AppExampleComponent {
    @Component.Factory
    interface Factory {
        fun create(): AppExampleComponent
    }

    fun inject(activity: ListFeatureActivity)
    fun inject(activity: ExampleCryptoActivity)
    fun inject(activity: ExampleNotificationActivity)
}
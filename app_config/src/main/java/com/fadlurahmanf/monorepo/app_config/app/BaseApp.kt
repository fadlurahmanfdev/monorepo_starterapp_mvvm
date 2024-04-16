package com.fadlurahmanf.monorepo.app_config.app

import android.app.Application
import com.fadlurahmanf.monorepo.app_config.di.component.ApplicationComponent
import com.fadlurahmanf.monorepo.app_config.di.component.DaggerApplicationComponent
import com.fadlurahmanf.monorepo.app_example.AppExampleComponent
import com.fadlurahmanf.monorepo.app_example.AppExampleProvider
import com.fadlurahmanf.monorepo.app_example.DaggerAppExampleComponent

class BaseApp : Application(), AppExampleProvider {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun provideAppExampleComponent(): AppExampleComponent =
        DaggerAppExampleComponent.factory().create()
}
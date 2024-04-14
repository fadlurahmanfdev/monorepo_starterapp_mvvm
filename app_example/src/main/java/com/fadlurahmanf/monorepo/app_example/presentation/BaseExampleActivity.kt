package com.fadlurahmanf.monorepo.app_example.presentation

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.fadlurahmanf.monorepo.app_example.others.di.AppExampleComponent
import com.fadlurahmanf.monorepo.app_example.others.di.AppExampleProvider
import com.fadlurahmanf.monorepo.app_shared.activity.BaseActivity
import com.fadlurahmanf.monorepo.app_shared.activity.InflateActivity

abstract class BaseExampleActivity<VB : ViewBinding>(
    inflater: InflateActivity<VB>
) : BaseActivity<VB>(inflater) {

    lateinit var component: AppExampleComponent

    override fun onBaseCreateSubComponent() {
        component =
            (applicationContext as AppExampleProvider).provideAppExampleComponent()
        onBaseExampleInjectActivity()
    }

    abstract fun onBaseExampleInjectActivity()

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        onBaseExampleCreate(savedInstanceState)
    }

    abstract fun onBaseExampleCreate(savedInstanceState: Bundle?)
}
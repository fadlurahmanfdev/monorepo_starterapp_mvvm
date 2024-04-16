package com.fadlurahmanf.monorepo.app_example.presentation.features.notification

import android.content.Context
import com.fadlurahmanf.monorepo.app_example.domain.usecases.ExampleNotificationUseCase
import com.fadlurahmanf.monorepo.app_shared.activity.BaseViewModel
import javax.inject.Inject

class ExampleNotificationViewModel @Inject constructor(
    private val exampleNotificationUseCase: ExampleNotificationUseCase
) : BaseViewModel() {

    fun showNotification(context: Context) =
        exampleNotificationUseCase.showSimpleNotification(context)
}
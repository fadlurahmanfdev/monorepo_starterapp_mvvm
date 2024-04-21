package com.fadlurahmanf.monorepo.app_example.presentation.features.notification

import android.content.Context
import android.util.Log
import com.fadlurahmanf.monorepo.app_example.domain.usecases.ExampleNotificationUseCase
import com.fadlurahmanf.monorepo.app_shared.activity.BaseViewModel
import javax.inject.Inject
import kotlin.random.Random

class ExampleNotificationViewModel @Inject constructor(
    private val exampleNotificationUseCase: ExampleNotificationUseCase
) : BaseViewModel() {

    fun askPermission(context: Context) {
        exampleNotificationUseCase.askPermissionNotification(
            context,
            onCompleteCheckPermission = { isGranted ->
                Log.d(
                    ExampleNotificationViewModel::class.java.simpleName,
                    "IS NOTIFICATION PERMISSION GRANTED: $isGranted"
                )
            }
        )
    }

    fun showNotification(context: Context) =
        exampleNotificationUseCase.showSimpleNotification(context)

    fun showMessagingNotification(context: Context) {
        exampleNotificationUseCase.showMessagingNotification(context)
    }

    fun showIncomingCallNotification(context: Context) {
        exampleNotificationUseCase.showIncomingCallNotification(context)
    }

    fun startIncomingCallPlayer(context: Context) {
        exampleNotificationUseCase.startIncomingCallPlayer(context)
    }

    fun stopIncomingCallPlayer(context: Context) {
        exampleNotificationUseCase.stopIncomingCallPlayer(context)
    }
}
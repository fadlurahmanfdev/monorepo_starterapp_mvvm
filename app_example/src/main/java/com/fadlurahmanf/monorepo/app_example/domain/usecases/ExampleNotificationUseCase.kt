package com.fadlurahmanf.monorepo.app_example.domain.usecases

import android.content.Context

interface ExampleNotificationUseCase {
    fun askPermissionNotification(context: Context, onCompleteCheckPermission: (Boolean) -> Unit)
    fun showSimpleNotification(context: Context)
    fun showMessagingNotification(context: Context)
    fun showIncomingCallNotification(context: Context)
    fun startIncomingCallPlayer(context: Context)
    fun stopIncomingCallPlayer(context: Context)
}
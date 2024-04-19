package com.fadlurahmanf.monorepo.app_example.domain.usecases

import android.content.Context

interface ExampleNotificationUseCase {
    fun askPermissionNotification(context: Context, onCompleteCheckPermission: (Boolean) -> Unit)
    fun showSimpleNotification(context: Context)
    fun showMessagingNotification(context: Context, id: Int)
}
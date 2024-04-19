package com.fadlurahmanf.monorepo.app_notification.data.repositories

import android.content.Context

interface AppNotificationRepository {
    fun askPermission(context: Context, onCompleteCheckPermission: (isGranted: Boolean) -> Unit)
    fun showNotification(context: Context, id: Int, title: String, message: String)
    fun showMessagingNotification(context: Context, id: Int)
}
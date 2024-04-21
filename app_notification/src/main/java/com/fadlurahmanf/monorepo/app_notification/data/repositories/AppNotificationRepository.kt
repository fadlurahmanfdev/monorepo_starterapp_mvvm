package com.fadlurahmanf.monorepo.app_notification.data.repositories

import android.app.Notification
import android.content.Context

interface AppNotificationRepository {
    fun askPermission(context: Context, onCompleteCheckPermission: (isGranted: Boolean) -> Unit)
    fun createVOIPChannel(context: Context)
    fun showNotification(context: Context, id: Int, title: String, message: String)
    fun showMessagingNotification(context: Context, id: Int)
    fun getIncomingCallNotification(
        context: Context,
        callerName: String,
        callerImage: String?
    ): Notification
}
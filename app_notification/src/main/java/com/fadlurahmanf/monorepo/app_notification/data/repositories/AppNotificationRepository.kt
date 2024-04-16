package com.fadlurahmanf.monorepo.app_notification.data.repositories

import android.content.Context

interface AppNotificationRepository {
    fun showNotification(context: Context, id: Int, title: String, message: String)
}
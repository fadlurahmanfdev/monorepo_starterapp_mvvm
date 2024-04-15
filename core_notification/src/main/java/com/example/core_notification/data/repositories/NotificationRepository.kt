package com.example.core_notification.data.repositories

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.annotation.DrawableRes
import com.example.core_notification.data.dto.model.ItemGroupedNotificationModel

interface NotificationRepository {
    /**
     * Determine whether <em>you</em> have been granted a particular permission.
     * @return isResult: [PackageManager.PERMISSION_GRANTED] if you have the
     * permission, or [PackageManager.PERMISSION_DENIED] if not.
     */
    fun askNotificationPermission(
        activity: Activity,
        onShouldShowRequestPermissionRationale: () -> Unit,
        onCompleteCheckPermission: (isGranted: Boolean, result: Int) -> Unit
    )

    fun isNotificationChannelExist(context: Context, channelId: String): Boolean

    fun createNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String,
        channelDescription: String,
        sound: Uri,
    )

    fun deleteNotificationChannel(
        context: Context,
        channelId: String,
    )

    fun showGeneralNotification(
        context: Context,
        id: Int,
        title: String,
        message: String,
        @DrawableRes smallIcon: Int,
        groupKey: String?,
        pendingIntent: PendingIntent?,
    )

    fun showGeneralImageNotification(
        context: Context,
        id: Int,
        title: String,
        message: String,
        imageUrl: String,
        @DrawableRes smallIcon: Int,
    )

    fun showCustomNotification(
        context: Context,
        id: Int,
        channelId: String,
        title: String,
        message: String,
        @DrawableRes smallIcon: Int,
        groupKey: String? = null,
        pendingIntent: PendingIntent? = null,
    )

    fun cancelNotification(context: Context, id: Int)

    fun showGroupedNotification(
        context: Context,
        id: Int,
        channelId: String,
        groupKey: String,
        bigContentTitle: String,
        summaryText: String,
        @DrawableRes smallIcon: Int,
        itemLine: List<String>,
        itemNotifications: List<ItemGroupedNotificationModel>,
    )
}
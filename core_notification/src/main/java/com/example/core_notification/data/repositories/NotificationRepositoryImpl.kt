package com.example.core_notification.data.repositories

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.core_notification.data.dto.model.ItemGroupedNotificationModel
import com.fadlurahmanf.monorepo.core_shared.CoreSharedConstant
import com.example.core_notification.others.BaseNotificationService
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor() : BaseNotificationService(),
    NotificationRepository {

    companion object {
        const val GENERAL_CHANNEL_ID = "GENERAL"
        const val GENERAL_CHANNEL_NAME = "Umum"
        const val GENERAL_CHANNEL_DESCRIPTION = "Notifikasi Umum"
    }

    override fun askNotificationPermission(
        activity: Activity,
        onShouldShowRequestPermissionRationale: () -> Unit,
        onCompleteCheckPermission: (isGranted: Boolean, result: Int) -> Unit
    ) {
        when {
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity, Manifest.permission_group.NOTIFICATIONS
            ) -> {
                onShouldShowRequestPermissionRationale()
            }

            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> {
                val isNotificationEnabled =
                    NotificationManagerCompat.from(activity).areNotificationsEnabled()
                onCompleteCheckPermission(
                    isNotificationEnabled,
                    if (isNotificationEnabled) PackageManager.PERMISSION_GRANTED else PackageManager.PERMISSION_DENIED
                )
            }

            else -> {
                val status = ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission_group.NOTIFICATIONS
                )
                onCompleteCheckPermission(status == PackageManager.PERMISSION_GRANTED, status)
            }
        }
    }

    override fun isNotificationChannelExist(context: Context, channelId: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val allChannels = getNotificationManager(context).notificationChannels
            var knownChannel: NotificationChannel? = null
            for (element in allChannels) {
                if (element.id == channelId) {
                    knownChannel = element
                    break
                }
            }
            return knownChannel != null
        }
        return false
    }

    override fun createNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String,
        channelDescription: String,
        sound: Uri,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isNotificationChannelExist(context, channelId)) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = channelDescription
                    setSound(sound, null)
                }
                getNotificationManager(context).createNotificationChannel(channel)
            }
        }
    }

    override fun deleteNotificationChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (isNotificationChannelExist(context, channelId)) {
                getNotificationManager(context).deleteNotificationChannel(channelId)
            }
        }
    }

    override fun showGeneralNotification(
        context: Context,
        id: Int,
        title: String,
        message: String,
        @DrawableRes smallIcon: Int,
        groupKey: String?,
        pendingIntent: PendingIntent?,
    ) {
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        createNotificationChannel(
            context,
            GENERAL_CHANNEL_ID,
            GENERAL_CHANNEL_NAME,
            GENERAL_CHANNEL_DESCRIPTION,
            sound,
        )
        val notification =
            notificationBuilderV2(context, GENERAL_CHANNEL_ID, smallIcon).apply {
                setContentTitle(title)
                setContentText(message)
                if (groupKey != null) {
                    setGroup(groupKey)
                }
                if (pendingIntent != null) {
                    setContentIntent(pendingIntent)
                }
            }
        getNotificationManager(context).notify(id, notification.build())
    }

    override fun showGeneralImageNotification(
        context: Context,
        id: Int,
        title: String,
        message: String,
        imageUrl: String,
        @DrawableRes smallIcon: Int,
    ) {
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        createNotificationChannel(
            context,
            GENERAL_CHANNEL_ID,
            GENERAL_CHANNEL_NAME,
            GENERAL_CHANNEL_DESCRIPTION,
            sound,
        )
        val notification = notificationBuilderV2(context, GENERAL_CHANNEL_ID, smallIcon).apply {
            setContentTitle(title)
            setContentText(message)
        }
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    notification.setLargeIcon(resource)
                    notification.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    getNotificationManager(context)
                        .notify(id, notification.build())
                }

                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    Log.e(CoreSharedConstant.LOGGER_TAG, "failed onLoadFailed")
                    getNotificationManager(context)
                        .notify(id, notification.build())
                }

            })
    }

    override fun showCustomNotification(
        context: Context,
        id: Int,
        channelId: String,
        title: String,
        message: String,
        @DrawableRes smallIcon: Int,
        groupKey: String?,
        pendingIntent: PendingIntent?,
    ) {
        val notification =
            notificationBuilderV2(context, channelId, smallIcon).apply {
                setContentTitle(title)
                setContentText(message)
                if (pendingIntent != null) {
                    setContentIntent(pendingIntent)
                }
                if (groupKey != null) {
                    setGroup(groupKey)
                }
            }
        getNotificationManager(context).notify(id, notification.build())
    }

    override fun cancelNotification(context: Context, id: Int) {
        getNotificationManager(context).cancel(id)
    }

    override fun showGroupedNotification(
        context: Context,
        id: Int,
        channelId: String,
        groupKey: String,
        bigContentTitle: String,
        summaryText: String,
        @DrawableRes smallIcon: Int,
        itemLine: List<String>,
        itemNotifications: List<ItemGroupedNotificationModel>,
    ) {
        val notifications: ArrayList<Notification> = arrayListOf()
        for (index in itemNotifications.indices) {
            notifications.add(
                notificationBuilderV2(
                    context,
                    channelId = channelId,
                    smallIcon = smallIcon
                ).apply {
                    setContentTitle(itemNotifications[index].title)
                    setContentText(itemNotifications[index].message)
                    setGroup(groupKey)
                }.build()
            )
        }
        val builder = groupNotificationBuilder(
            context,
            channelId = channelId,
            groupKey = groupKey,
            bigContentTitle = bigContentTitle,
            summaryText = summaryText,
            lines = itemLine,
            smallIcon = smallIcon,
        )
        getNotificationManager(context).apply {
            for (index in itemNotifications.indices) {
                notify(itemNotifications[index].id, notifications[index])
            }
            notify(id, builder.build())
        }
    }
}
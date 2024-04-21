package co.id.fadlurahmanf.core_call_notification.domain.services

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

abstract class CallNotificationPlayer : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var audioManager: AudioManager
    private lateinit var vibrator: Vibrator

    private var currentRingerMode: Int = AudioManager.RINGER_MODE_SILENT
    private var isRingerModeListenerRegistered: Boolean = false

    companion object {
        const val SHOW_INCOMING_CALL_NOTIFICATION =
            "com.github.fadlurahmanfdev.SHOW_INCOMING_CALL_NOTIFICATION"
        const val ACCEPT_INCOMING_CALL =
            "com.github.fadlurahmanfdev.ACCEPT_INCOMING_CALL"
        const val DECLINE_INCOMING_CALL =
            "com.github.fadlurahmanfdev.DECLINE_INCOMING_CALL"

        const val PARAM_CALL_NOTIFICATION_ID =
            "PARAM_CALL_NOTIFICATION_ID"
        const val PARAM_CALLER_NAME =
            "PARAM_CALLER_NAME"
        const val PARAM_CALLER_NETWORK_IMAGE =
            "PARAM_CALLER_NETWORK_IMAGE"
    }

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(
            CallNotificationPlayer::class.java.simpleName,
            "ACTION COMING -> ${intent?.action}"
        )
        when (intent?.action) {
            SHOW_INCOMING_CALL_NOTIFICATION -> {
                val callNotificationId = intent.getIntExtra(PARAM_CALL_NOTIFICATION_ID, -1)
                val callerName = intent.getStringExtra(PARAM_CALLER_NAME)
                val callerImage = intent.getStringExtra(PARAM_CALLER_NETWORK_IMAGE)
                if (callNotificationId != -1) {
                    onStartForegroundIncomingCallNotification(
                        callNotificationId,
                        callerName = callerName ?: "-",
                        callerImage = callerImage
                    )
                    onIncomingNotificationPlayerPlaying()
                }
            }

            ACCEPT_INCOMING_CALL -> {
                onAcceptIncomingCallNotification()
            }

            DECLINE_INCOMING_CALL -> {
                onDeclineIncomingCallNotification()
            }
        }
        return START_STICKY
    }

    abstract fun onGetIncomingCallNotification(
        callNotificationId: Int,
        callerName: String,
        callerImageBitmap: Bitmap?
    ): Notification

    open fun onStartForegroundIncomingCallNotification(
        callNotificationId: Int,
        callerName: String,
        callerImage: String?
    ) {
        if (callerImage != null) {
            Glide.with(applicationContext).asBitmap().load(callerImage)
                .into(
                    object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            Log.d(
                                CallNotificationPlayer::class.java.simpleName,
                                "on resource bitmap caller image ready"
                            )
                            startForeground(
                                callNotificationId, onGetIncomingCallNotification(
                                    callNotificationId = callNotificationId,
                                    callerName = callerName,
                                    callerImageBitmap = resource,
                                )
                            )
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            Log.d(
                                CallNotificationPlayer::class.java.simpleName,
                                "on resource bitmap caller image failed"
                            )
                            startForeground(
                                callNotificationId, onGetIncomingCallNotification(
                                    callNotificationId = callNotificationId,
                                    callerName = callerName,
                                    callerImageBitmap = null,
                                )
                            )
                        }
                    },
                )
        } else {
            startForeground(
                callNotificationId, onGetIncomingCallNotification(
                    callNotificationId = callNotificationId,
                    callerName = callerName,
                    callerImageBitmap = null,
                )
            )
        }
    }

    open fun onIncomingNotificationPlayerPlaying() {
        stopRinging()
        startRinging()
        listenRingerMode()
    }

    private fun onAcceptIncomingCallNotification() {
        stopForegroundService()
    }

    open fun onDeclineIncomingCallNotification() {
        stopForegroundService()
    }

    private fun startRinging() {
        when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> {
                startRingingNormalMode()
            }

            AudioManager.RINGER_MODE_VIBRATE -> {
                startRingingVibrateMode()
            }

            AudioManager.RINGER_MODE_SILENT -> {
                startRingingSilentMode()
            }
        }
    }

    private val ringerModeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                AudioManager.RINGER_MODE_CHANGED_ACTION -> {
                    if (currentRingerMode == audioManager.ringerMode) {
                        return
                    }
                    when (audioManager.ringerMode) {
                        AudioManager.RINGER_MODE_NORMAL -> {
                            stopRinging()
                            startRingingNormalMode()
                            listenRingerMode()
                        }

                        AudioManager.RINGER_MODE_VIBRATE -> {
                            stopRinging()
                            startRingingVibrateMode()
                            listenRingerMode()
                        }

                        AudioManager.RINGER_MODE_SILENT -> {
                            stopRinging()
                            startRingingSilentMode()
                            listenRingerMode()
                        }
                    }
                }
            }
        }
    }

    private fun startRingingNormalMode() {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(applicationContext, soundUri)

        // set volume based on ringtone volume
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
                .build()
        )

        mediaPlayer?.prepare()
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        playVibrateEffect()
        currentRingerMode = AudioManager.RINGER_MODE_NORMAL
    }

    private fun startRingingVibrateMode() {
        playVibrateEffect()
        currentRingerMode = AudioManager.RINGER_MODE_VIBRATE
    }

    private fun startRingingSilentMode() {
        currentRingerMode = AudioManager.RINGER_MODE_SILENT
    }

    private fun playVibrateEffect() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0L, 2000L, 2000L), 0))
        } else {
            vibrator.vibrate(longArrayOf(0L, 2000L, 2000L), 0)
        }
    }

    private fun listenRingerMode() {
        if (!isRingerModeListenerRegistered) {
            registerReceiver(
                ringerModeReceiver,
                IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
            )
        }
        isRingerModeListenerRegistered = true
    }

    private fun stopRinging() {
        stopListenRingerMode()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        cancelVibrator()
    }

    private fun stopListenRingerMode() {
        if (isRingerModeListenerRegistered) {
            unregisterReceiver(ringerModeReceiver)
        }
        isRingerModeListenerRegistered = false
    }

    private fun cancelVibrator() {
        vibrator.cancel()
    }

    override fun onDestroy() {
        stopRinging()
        super.onDestroy()
    }

    private fun stopForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }
    }
}
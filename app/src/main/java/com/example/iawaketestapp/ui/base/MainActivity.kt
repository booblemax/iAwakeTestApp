package com.example.iawaketestapp.ui.base

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.iawaketestapp.R
import com.example.iawaketestapp.ui.main.MainFragment
import com.example.iawaketestapp.ui.tracks.TracksService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PlaybackCallback {

    @Inject
    lateinit var musicStateManager: MusicStateManager

    private var service: TracksService? = null
    private lateinit var broadcastReceiver: BroadcastReceiver
    private var bound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TracksService.MusicBinder
            this@MainActivity.service = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            this@MainActivity.service = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val tm = context.getSystemService(Service.TELEPHONY_SERVICE) as? TelephonyManager
                if (tm != null) {
                    if (tm.callState == TelephonyManager.CALL_STATE_RINGING) {
                        if (service?.isPlaying() == true) {
                            service?.stop()
                        }
                    }
                }
                val playerState = intent.getIntExtra("state", 0)
                lifecycleScope.launch {
                    musicStateManager.updatePlayingState(playerState)
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction("android.intent.action.PHONE_STATE")
        registerReceiver(broadcastReceiver, filter)

        createNotificationChannel()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment.newInstance())
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this@MainActivity, TracksService::class.java)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
        LocalBroadcastManager.getInstance(this).registerReceiver(
            broadcastReceiver,
            IntentFilter("com.example.iawaketestapp.PLAYER_STATUS")
        )
    }

    override fun onDestroy() {
        if (bound) {
            unbindService(serviceConnection)
            bound = false
        }
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    override fun play(url: String) {
        service?.play(url)
    }

    override fun resume() {
        service?.resume()
    }

    override fun pause() {
        service?.pause()
    }

    override fun stop() {
        service?.stop()
    }

    override fun getCurrentProgress(): Int = service?.getCurrentProgress()?.toInt() ?: 0

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Streaming Radio"
            val description = "channel for streaming radio"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("playback_channel", name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
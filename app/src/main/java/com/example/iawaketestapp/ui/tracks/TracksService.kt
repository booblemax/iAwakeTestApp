package com.example.iawaketestapp.ui.tracks

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TracksService : Service() {

    private val TAG = "MusicService";

    private lateinit var player: ExoPlayer
    private lateinit var broadcaster: LocalBroadcastManager
    private val mBinder = MusicBinder()
    private lateinit var playerNotificationManager: PlayerNotificationManager

    @Override
    override fun onCreate() {
        val bandwidthMeter = DefaultBandwidthMeter.Builder(applicationContext).build()
        val trackSelectionFactory = AdaptiveTrackSelection.Factory()
        val trackSelector = DefaultTrackSelector(applicationContext, trackSelectionFactory)
        player = ExoPlayer.Builder(applicationContext)
            .setBandwidthMeter(bandwidthMeter)
            .setTrackSelector(trackSelector)
            .build()
        broadcaster = LocalBroadcastManager.getInstance(this);

        player.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                val intent = Intent("com.example.iawaketestapp.PLAYER_STATUS")
                when (playbackState) {
                    ExoPlayer.STATE_BUFFERING -> {
                        intent.putExtra("state", PlaybackStateCompat.STATE_BUFFERING)
                        broadcaster.sendBroadcast(intent)
                    }
                    ExoPlayer.STATE_READY -> {
                        if (playWhenReady) {
                            intent.putExtra("state", PlaybackStateCompat.STATE_PLAYING)
                        } else {
                            intent.putExtra("state", PlaybackStateCompat.STATE_PAUSED)
                        }
                    }
                }
                broadcaster.sendBroadcast(intent)
            }
        })
        playerNotificationManager = PlayerNotificationManager.Builder(this, 100, "playback_channel")
            .setMediaDescriptionAdapter(DefaultAdapter(this))
            .build()
            .apply {
                setPlayer(player)
                setUseNextAction(false)
                setUsePreviousAction(false)
                setUseFastForwardAction(false)
                setUseRewindAction(false)
                setUseStopAction(false)

                setColor(Color.BLACK)
                setColorized(true)
                setUseChronometer(true)
                setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
            }

        super.onCreate();
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? = mBinder

    override fun onUnbind(intent: Intent?): Boolean {
        playerNotificationManager.setPlayer(null);
        return super.onUnbind(intent)
    }

    fun play(channelUrl: String) {
        val dataSourceFactory = DefaultDataSource.Factory(this)
        val extractorsFactory = DefaultExtractorsFactory()
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory)
            .createMediaSource(MediaItem.fromUri(channelUrl))
        player.setMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = true;
    }

    fun stop() {
        player.playWhenReady = false;
        player.stop();
    }

    fun isPlaying(): Boolean = player.playbackState == Player.STATE_READY

    inner class MusicBinder : Binder() {

        fun getService(): TracksService = this@TracksService
    }
}
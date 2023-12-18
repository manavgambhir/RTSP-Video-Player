package com.example.rtspvideoplayer

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.media3.exoplayer.ExoPlayer
import com.example.rtspvideoplayer.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    lateinit var player : ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val recUrl = intent.getStringExtra("url")
        Toast.makeText(this, "$recUrl", Toast.LENGTH_SHORT).show()

        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }

                    Player.STATE_READY, Player.STATE_ENDED -> {
                        binding.progressBar2.visibility = View.GONE
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)

                val connectivityManager =
                    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo

                if (networkInfo == null || !networkInfo.isConnected) {
                    AlertDialog.Builder(this@MainActivity2)
                        .setTitle("Network Error")
                        .setMessage("Please check your internet connection and try again.")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(this@MainActivity2, MainActivity::class.java)
                            startActivity(intent)
                        }
                        .create()
                        .show()
                }
            }
        })
        player.setMediaItem(MediaItem.fromUri(recUrl!!))
        player.prepare()
        player.play()

        binding.button2.setOnClickListener {
            player.stop()
            val intent = Intent(this@MainActivity2, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

    override fun onStop() {
        super.onStop()
        player.pause()
    }
}
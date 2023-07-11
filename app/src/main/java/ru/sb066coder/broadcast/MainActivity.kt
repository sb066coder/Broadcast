package ru.sb066coder.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {


    private var countClicks = 0

    private lateinit var progressBar: ProgressBar

    private var loadingReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "loaded") {
                val percent = intent.getIntExtra("percent", 0)
                progressBar.progress = percent
            }
        }
    }
    private val receiver = MyReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.prBar)
        findViewById<Button>(R.id.btn).setOnClickListener {
            Intent().apply {
                action = MyReceiver.ACTION_CLICKED
                putExtra(MyReceiver.CLICKS, ++countClicks)
                sendBroadcast(this)
            }
        }
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(MyReceiver.ACTION_CLICKED)
            addAction("loaded")
        }
        registerReceiver(receiver, intentFilter)
        registerReceiver(loadingReceiver, intentFilter)
        startService(Intent(this, MyService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(loadingReceiver)
        unregisterReceiver(receiver)
    }
}
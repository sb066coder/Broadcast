package ru.sb066coder.broadcast

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlin.concurrent.thread

class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            for (i in 1..10) {
                Thread.sleep(1000)
                Intent("loaded").apply {
                    putExtra("percent", i * 10)
                    sendBroadcast(this)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}
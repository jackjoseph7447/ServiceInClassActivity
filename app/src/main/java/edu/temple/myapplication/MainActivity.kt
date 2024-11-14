package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var timerTextView = findViewById<TextView>(R.id.textView)

    lateinit var timerBinder: TimerService.TimerBinder
    var isConnected = false

    val timerHandler = Handler(Looper.getMainLooper())
    {
        timerTextView.text = it.what.toString()

        true
    }

    val connection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerBinder = service as TimerService.TimerBinder
            timerBinder.setHandler(timerHandler)
            isConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isConnected = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            bindService(
                Intent(this, TimerService::class.java),
                connection,
                BIND_AUTO_CREATE
            )
        }

        findViewById<View>(R.id.startButton).setOnClickListener {
            if(isConnected)
            {
                timerBinder.start(100)
            }
        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {

        }
    }
}
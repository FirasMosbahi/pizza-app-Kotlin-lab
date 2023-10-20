package com.gl4.pizzadelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import kotlinx.coroutines.delay
import java.time.Duration

class MainActivity : AppCompatActivity() {
    private val handler : Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image : ImageView = findViewById<ImageView>(R.id.imageView)
        image.setImageResource(R.drawable.pizza)
        handler.postDelayed(
            {
                val intent = Intent(this, DeliveryActivity::class.java)
                startActivity(intent)
                finish()
            }, 5000
        )

    }
}
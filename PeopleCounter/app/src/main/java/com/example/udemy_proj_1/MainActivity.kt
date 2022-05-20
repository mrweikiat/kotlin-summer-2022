package com.example.udemy_proj_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.click_me_button)
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "0"
        button.text = "Click me!"
        var counter = 0;

        button.setOnClickListener {
            counter = counter + 1
            textView.text = counter.toString()
        }
    }
}
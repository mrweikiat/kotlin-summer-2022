package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.btnStart)
        val etName: EditText = findViewById(R.id.appCompatEditText)
        btnStart.setOnClickListener {
            if(etName.text.isEmpty()) {
                Toast.makeText(this,
                    "Name cannot be empty!", Toast.LENGTH_LONG).show()
            } else {
                // create intent to navigate to the quiz activity
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, etName.text.toString())
                startActivity(intent)
                finish()
            }
        }

    }
}
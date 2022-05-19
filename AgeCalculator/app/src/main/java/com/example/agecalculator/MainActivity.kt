package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var birthDate : TextView? = null
    private var minutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // button for google calender
        val btnDatePicker: Button = findViewById(R.id.button_select_date)
        birthDate = findViewById(R.id.birthDate)
        minutes = findViewById(R.id.tvMinutes)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    fun clickDatePicker() {

        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{view,year,month,day ->

                val selectedDate = "$day/${month+1}/$year"
                birthDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)

                val answer = theDate.time / 60000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currentDateInMin = currentDate.time / 60000
                val difference = currentDateInMin - answer
                if (difference < 0) {
                    Toast.makeText(this,
                        "You haven't been born yet!",
                        Toast.LENGTH_LONG).show()
                    birthDate?.text = "-"
                    minutes?.text = "-"
                } else {
                    minutes?.text = difference.toString()
                }

            }
            , year, month, day
        ).show()
    }
}
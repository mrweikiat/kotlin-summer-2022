package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarExercise)

        // activate back button
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        binding?.toolBarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }
        setUpRestView()

    }

    private fun setUpRestView() {
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE

        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setUpExerciseView() {
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.ivImage?.setImageResource(
            exerciseList!![currentExercisePosition].getImage()
        )

        binding?.tvExerciseName?.text =
            exerciseList!![currentExercisePosition].getName()


        setExerciseProgressBar()


    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        restTimer = object: CountDownTimer(11000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 11 - restProgress
                binding?.tvTimer?.text = (11 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setUpExerciseView()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding = null
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(30000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    setUpRestView()
                }else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congrats!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }
}
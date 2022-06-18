package com.example.workoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityExerciseBinding
import com.example.workoutapp.databinding.DialogCustomBackConfirmationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    private var restTimerDuration: Long = 10
    private var exerciseTimerDuration: Long = 30

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

        tts = TextToSpeech(this, this)

        binding?.toolBarExercise?.setNavigationOnClickListener{
            customDialogForBackButton()
        }
        setUpRestView()
        setUpExerciseStatusRecyclerView()

    }

    override fun onBackPressed() {
        customDialogForBackButton()
        //super.onBackPressed()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding
            .inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.tvYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }

        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setUpExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setUpRestView() {

        try{
            val soundURI = Uri.parse(
                "android.resource://com.example.workoutapp/" +
                        R.raw.bell)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()

        } catch(e: Exception) {
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE

        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        speakOut("rest for 10 seconds")

        binding?.tvUpcomingExerciseName?.text =
            exerciseList!![currentExercisePosition+1]
                .getName()

        setRestProgressBar()
    }

    private fun setUpExerciseView() {
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE

        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        // text to speech
        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.ivImage?.setImageResource(
            exerciseList!![currentExercisePosition].getImage()
        )

        binding?.tvExerciseName?.text =
            exerciseList!![currentExercisePosition].getName()


        setExerciseProgressBar()


    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        // setting it to 2s for now
        restTimer = object: CountDownTimer(restTimerDuration*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 11 - restProgress
                binding?.tvTimer?.text = (11 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()


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

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null) {
            player!!.stop()
        }

        binding = null
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(
            exerciseTimerDuration*1000,
            1000
        ){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                if(currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                }else {
                    speakOut("Stop")
                    finish()
                    val intent = Intent(
                        this@ExerciseActivity,
                        FinishActivity::class.java
                    )
                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Log.e("TTS", "The language specified is not available")
            } else {
                Log.e("TTS", "Init failed")
            }
        }

    }

    private fun speakOut(text: String){
        tts!!.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            ""
        )
    }
}
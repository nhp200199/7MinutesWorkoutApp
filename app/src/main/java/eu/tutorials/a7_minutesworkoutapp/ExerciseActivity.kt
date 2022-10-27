package eu.tutorials.a7_minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tutorials.a7_minutesworkoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding
    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var currentExercisePosition: Int = -1
    private lateinit var exercisesList: ArrayList<ExerciseModel>
    private lateinit var tts: TextToSpeech
    private var madapter: ExerciseAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "Exercise"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        tts = TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) {
                val result = tts!!.setLanguage(Locale.US)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this@ExerciseActivity, "Text To Speech not available", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this@ExerciseActivity, "Text To Speech init fails", Toast.LENGTH_SHORT).show()
            }
        }

        exercisesList = Constants.defaultExerciseList()

        startRestTimer()

        madapter = ExerciseAdapter(exercisesList)
        binding.rcvExercisesContainer.apply {
            this.adapter = madapter
            layoutManager = LinearLayoutManager(this@ExerciseActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun startRestTimer() {
        restTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(remainingTime: Long) {
                val remainingTimeInSecs = (remainingTime / 1000).toInt()
                updateTimeRemainingUI(remainingTimeInSecs)
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "finish", Toast.LENGTH_SHORT).show()
                currentExercisePosition = 0
                exercisesList[currentExercisePosition].isSelected = true
                madapter?.notifyDataSetChanged()
                updateExerciseUI()
                startExerciseTimer()
            }
        }
        restTimer?.start()
    }

    private fun startExerciseTimer() {
        exerciseTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(remainingTime: Long) {
                val remainingTimeInSecs = (remainingTime/1000).toInt()
                updateTimeRemainingUI(remainingTimeInSecs)
            }

            override fun onFinish() {
                val previousExercise = currentExercisePosition
                exercisesList[previousExercise].isSelected = false
                exercisesList[previousExercise].isCompleted = true
                madapter?.notifyDataSetChanged()

                if (currentExercisePosition < exercisesList.size - 1) {
                    currentExercisePosition++
                    exercisesList[currentExercisePosition].isSelected = true
                    updateExerciseUI()
                    madapter?.notifyDataSetChanged()
                    exerciseTimer?.start()
                }
                else {
                    startActivity(Intent(this@ExerciseActivity, FinishActivity::class.java))
                    finish()
                }
            }
        }

        exerciseTimer?.start()
    }

    private fun updateExerciseUI() {
        val currentExercise = exercisesList[currentExercisePosition]
        binding.ivExercise.setImageResource(currentExercise.imageRes)

        tts.speak(currentExercise.name, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun updateTimeRemainingUI(remainingTimeInSecs: Int) {
        binding.progressBar.progress = remainingTimeInSecs
        binding.tvRemainingTime.text = remainingTimeInSecs.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearTimer()
        tts.stop()
        tts.shutdown()
    }

    private fun clearTimer() {
        restTimer?.cancel()
        exerciseTimer?.cancel()
    }
}
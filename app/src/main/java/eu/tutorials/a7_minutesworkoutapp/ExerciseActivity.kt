package eu.tutorials.a7_minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import eu.tutorials.a7_minutesworkoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding
    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
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

        startRestTimer()
    }

    private fun startRestTimer() {
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(remainingTime: Long) {
                val remainingTimeInSecs = (remainingTime / 1000).toInt()
                updateTimeRemainingUI(remainingTimeInSecs)
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "finish", Toast.LENGTH_SHORT).show()
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
                exerciseTimer?.start()
            }
        }

        exerciseTimer?.start()
    }

    private fun updateTimeRemainingUI(remainingTimeInSecs: Int) {
        binding.progressBar.progress = remainingTimeInSecs
        binding.tvRemainingTime.text = remainingTimeInSecs.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearTimer()
    }

    private fun clearTimer() {
        restTimer?.cancel()
        exerciseTimer?.cancel()
    }
}
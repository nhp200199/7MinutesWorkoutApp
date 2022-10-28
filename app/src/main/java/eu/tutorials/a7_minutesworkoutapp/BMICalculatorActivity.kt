package eu.tutorials.a7_minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.tutorials.a7_minutesworkoutapp.databinding.ActivityBmiCalculatorBinding

class BMICalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.a.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "BMI Activity"
        }
        binding.a.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
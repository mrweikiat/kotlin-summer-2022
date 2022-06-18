package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener{_, checkedId: Int ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUSUnitsView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE

        binding?.tilUSMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUSUnitHeightFeet?.visibility = View.INVISIBLE
        binding?.tilMetricUSUnitHeightInch?.visibility = View.INVISIBLE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE

        binding?.tilUSMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUSUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilMetricUSUnitHeightInch?.visibility = View.VISIBLE

        binding?.etUsMetricUnitWeight?.text!!.clear()
        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE

    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "You really need to eat more..."
        } else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "You need to eat more..."
        } else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "Eat more..."
        } else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Optimal"
            bmiDescription = "Great job! Keep living the active lifestyle"
        } else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Start exercising and eat moderately."
        } else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Obese"
            bmiDescription = "Need to exercise and eat moderately."
        } else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "Severely Obese"
            bmiDescription = "Exercise and eat healthy to avoid dying young"
        } else {
            bmiLabel = "Very severely Obese"
            bmiDescription = "Danger to health, urgent help required"
        }

        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }else if (binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateUSUnits(): Boolean{
        var isValid = true
        when {
            binding?.etUsMetricUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val heightValue: Float =
                    binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weightValue: Float =
                    binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue*heightValue)

                displayBMIResult(bmi)

            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        } else if (currentVisibleView == US_UNITS_VIEW){
            if(validateUSUnits()){
                val usUnitHeightValueFeet: String =
                    binding?.etUsMetricUnitHeightFeet?.text.toString()
                val usUnitHeightValueInch: String =
                    binding?.etUsMetricUnitHeightInch?.text.toString()
                val usUnitWeightValue: Float =
                    binding?.etUsMetricUnitWeight?.text.toString().toFloat()

                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                val bmi = 703 * (usUnitWeightValue/(heightValue*heightValue))

                displayBMIResult(bmi)

            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
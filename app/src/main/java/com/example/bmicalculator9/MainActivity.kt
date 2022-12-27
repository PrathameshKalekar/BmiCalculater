package com.example.bmicalculator9

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

            private  lateinit var  sf : SharedPreferences
            private lateinit var editor :SharedPreferences.Editor
            private lateinit var Weight : EditText
            private lateinit var Height : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            Weight = findViewById(R.id.enterWeight)
            Height = findViewById(R.id.enterHeight)
            sf = getSharedPreferences("my_sf", MODE_PRIVATE)
            editor= sf.edit()

        val calculate = findViewById<Button>(R.id.Calculate)

        calculate.setOnClickListener {
            val getWeight = Weight.text.toString()
            val getHeight = Height.text.toString()

                if (validateInput(getWeight,getHeight)) {
                    val bmi = getWeight.toFloat() / ((getHeight.toFloat() / 100) * (getHeight.toFloat() / 100))
                    val bmi2digit = String.format("%.2f", bmi).toFloat()
                    displayResult(bmi2digit)

            }
        }
    }
    private fun validateInput(Weight:String?,Height:String?):Boolean{
        return when {
            Weight.isNullOrEmpty() -> {
                Toast.makeText(this,"Please enter Weight",Toast.LENGTH_SHORT).show()
                return false
            }
            Height.isNullOrEmpty() ->{
                Toast.makeText(this,"Please enter height",Toast.LENGTH_SHORT).show()
                return false
            }
            else ->{
                return true
            }

        }
    }

    private fun displayResult(bmi : Float){
            val textIndex = findViewById<TextView>(R.id.ResultView)
            val textDescription =findViewById<TextView>(R.id.ResultView2)
            val info = findViewById<TextView>(R.id.ResultView3)


                textIndex.text = bmi.toString()
                info.text = "(normal range is 18.0 - 29.8)"

                var resulText = ""
                var color = 0

        when {
            bmi < 18.50 ->{
                resulText = "Under Weight"
                color = R.color.underWeight
            }
            bmi in 18.51..24.90->{
                resulText = "Prefect weight"
                color = R.color.perfext
            }
            bmi in 25.00..29.90 ->{
                resulText = "Over Weight"
                color = R.color.overWeight
            }
            bmi > 29.91 -> {
                resulText = "Obese"
                color = R.color.obese
            }
        }
        textDescription.setTextColor(ContextCompat.getColor(this,color))
        textDescription.text = resulText
    }

    override fun onPause() {
        super.onPause()
        val weight = Weight.text.toString()
        val height = Height.text.toString()
        editor.apply {
            putString("sf_weight",weight)
            putString("sf_height",height)
            commit()
        }
    }


    override fun onResume() {
        super.onResume()
        val weight = sf.getString("sf_weight",null)
        val height = sf.getString("sf_height",null)
        Weight.setText(weight)
        Height.setText(height)
    }
}


package com.example.mycalkutor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.mycalkutor.R

class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view : View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        //tvInput.append("1")
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view : View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDotted(view: View){
        if(lastNumeric && !lastDot){
            val tvInput = findViewById<TextView>(R.id.tvInput)
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }


    fun onEqual(view: View){
        if(lastNumeric){
            val tvInput = findViewById<TextView>(R.id.tvInput)
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try {
                val tvValue = tvInput.text.toString()
                var result: Double? = null



                when {
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        try {
                            val one = splitValue[0].toDouble()
                            val two = splitValue[1].toDouble()
                            result = one + two
                        } catch (e:NumberFormatException){
                            result = Double.NaN
                        }
                    }

                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        try{
                            val one = splitValue[0].toDouble()
                            val two = splitValue[1].toDouble()
                            result = one - two
                        } catch (e: NumberFormatException){
                            result = Double.NaN
                        }
                    }

                    tvValue.contains("x") -> {
                        val splitValue = tvValue.split("x")
                        try{
                            val one = splitValue[0].toDouble()
                            val two = splitValue[1].toDouble()
                            result = one * two
                        } catch (e: NumberFormatException){
                            result = Double.NaN
                        }
                    }

                    tvValue.contains("÷") -> {
                        val splitValue = tvValue.split("÷")
                        try {
                            val one = splitValue[0].toDouble()
                            val two = splitValue[1].toDouble()
                            if (two == 0.0) {
                                tvInput.text = "undefined"
                                return
                            } else {
                                result = one / two
                            }
                        } catch (e:NumberFormatException){
                            result = Double.NaN
                        }

                    }
                }


                    val formattedResult = if (result == null) {
                        "NaN"
                    } else {
                        prefix + String.format("%.2f", result)

                    }
                   tvInput.text = removeLastDot(formattedResult)

            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeLastDot(result:String):String{
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    fun onOperator(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")){
            false
        } else{
            value.contains("÷") || value.contains("x")
                    || value.contains("-") || value.contains("+")

        }
    }
}




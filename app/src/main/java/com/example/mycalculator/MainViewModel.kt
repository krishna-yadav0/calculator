package com.example.mycalculator

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class MainViewModel: ViewModel() {



    var lastNumaric: Boolean = false
    var stateError: Boolean = false
    var lastDot :Boolean = false


    val outputText = MutableLiveData("")





    fun onDigit(view: View)
    {
        if(stateError)
        {
            outputText.value=(view as Button).text.toString()
            stateError=false
        }else {
            val digit = (view as Button).text.toString()
            outputText.value=outputText.value+digit
        }
        lastNumaric=true
    }

    fun onDecimalPoint(view: View)
    {
        if(lastNumaric && !stateError && !lastDot)
        {
            outputText.value=outputText.value+"."

            lastNumaric=false
            lastDot=true
        }
    }

    fun onOperator (view: View)
    {
        if(lastNumaric && !stateError)
        {
            val operator = (view as Button).text.toString()
            outputText.value=outputText.value+operator

            lastNumaric=false
            lastDot=false
        }
    }


    fun onClear(view: View)
    {
        this.outputText.value= ""
        lastNumaric=false
        stateError=false
        lastDot=false
    }
    fun onEqual(view: View)
    {

        if(lastNumaric && !stateError)
        {
            val text = outputText.value.toString()
            val expression= ExpressionBuilder(text).build()
            try
            {
                val result= expression.evaluate()
                outputText.value= result.toString()
                lastDot=true
            }catch (ex:Exception)
            {
                outputText.value="Error"
                stateError=true
                lastNumaric=false
            }
        }

    }



}
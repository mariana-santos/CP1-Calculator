package com.fiap.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fiap.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var display = ""
    private val operations = listOf("%", "×", "÷", "+", "-")
    private val numbers = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0")

    private var operation = ""
    private var number1: Int? = null
    private var number2: Int? = null
    private var negative = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botões que adicionam números ao display
        binding.btnZero.setOnClickListener { appendItem("0") }
        binding.btnOne.setOnClickListener { appendItem("1") }
        binding.btnTwo.setOnClickListener { appendItem("2") }
        binding.btnThree.setOnClickListener { appendItem("3") }
        binding.btnFour.setOnClickListener { appendItem("4") }
        binding.btnFive.setOnClickListener { appendItem("5") }
        binding.btnSix.setOnClickListener { appendItem("6") }
        binding.btnSeven.setOnClickListener { appendItem("7") }
        binding.btnEight.setOnClickListener { appendItem("8") }
        binding.btnNine.setOnClickListener { appendItem("9") }

        // Botões que adicionam operações ao display
        binding.btnMinus.setOnClickListener { appendItem("-") }
        binding.btnPlus.setOnClickListener { appendItem("+") }
        binding.btnDivide.setOnClickListener { appendItem("÷") }
        binding.btnMultiply.setOnClickListener { appendItem("×") }
        binding.btnPercentage.setOnClickListener { appendItem("%") }

        // Outros botões
        binding.btnChangeSign.setOnClickListener { changeSign() }
        binding.btnResult.setOnClickListener { calculate() }
        binding.btnCE.setOnClickListener { clearEverything() }
    }

    private fun appendItem(item: String) {
        val isOperation = operations.any { item == it }
        val hasOperation = operation !== ""
        val hasNumber = number1 !== null
        val isNumber = numbers.any { item.contains(it) }

        if (!hasNumber && isOperation) return;

        if (hasOperation && isNumber) {
            val currentNumber2 = if (number2 !== null) number2 else ""
            number2 = "${currentNumber2}${item}".toInt()
        }

        if (!hasOperation && isNumber) {
            val currentNumber1 = if (number1 !== null) number1 else ""
            number1 = "${currentNumber1}${item}".toInt()
        }

        if (!hasOperation && isOperation)
            operation = item

        if (isOperation && hasOperation) return calculate();

        display += item
        binding.edtValue.setText(display)
    }

    private fun clearEverything() {
        binding.edtValue.setText("")
        number1 = null
        number2 = null
        operation = ""
        display = ""
        negative = false
    }

    private fun changeSign() {
        if (!negative && display !== "") {
            display = "-$display"
            negative = true
            return binding.edtValue.setText(display)
        }

        // não caiu no if; remove o sinal de menos
        display = display.substring(1)
        binding.edtValue.setText(display)
        negative = false
    }

    private fun calculate() {
        var result = 0

        if (number1 !== null && number2 !== null) {
            if (operation == "+") result = number1!! + number2!!
            if (operation == "-") result = number1!! - number2!!
            if (operation == "÷") result = number1!! / number2!!
            if (operation == "×") result = number1!! * number2!!
            if (operation == "%") result = number1!! * number2!! / 100
        }

        if (negative) {
            result -= (result * 2)
        }

        binding.edtValue.setText("$result")

        number1 = result
        number2 = null
        operation = ""
        display = "$result"
        negative = result < 0
    }
}
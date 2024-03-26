package com.fiap.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fiap.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    var display = ""

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

        // Botões que "limpam" o display
        binding.btnErase.setOnClickListener { backspace() }
        binding.btnCE.setOnClickListener { clearEverything() }

        // Outros botões
        binding.btnChangeSign.setOnClickListener { changeSign() }

    }

    private fun appendItem(item: String) {
        val operations = listOf("%", "×", "÷");
        val numbers = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0");

        val startsWithNumber = numbers.any { display.startsWith(it) }
        val isOperation = operations.any { item.equals(it) }

        // Usuário não pode colocar uma operação antes de selecionar um número
        if (!startsWithNumber && isOperation) {
            return;
        }

        display += item
        binding.edtValue.setText(display)
    }

    private fun clearEverything() {
        display = ""
        binding.edtValue.setText(display)
    }

    private fun backspace() {
        if (display.isNotEmpty()) {
            display = display.substring(0, display.length - 1)
            binding.edtValue.setText(display)
        }
    }

    private fun changeSign() {
        if (!display.startsWith("-")) {
            display = "-$display"
            return binding.edtValue.setText(display);
        }

        // não caiu no if; remove o sinal de menos
        display = display.substring(1)
        binding.edtValue.setText(display)
    }
}
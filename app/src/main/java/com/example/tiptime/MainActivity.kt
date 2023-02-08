package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener{calculateTip()}

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
    }

    // Calcular a gorjeta
    private fun calculateTip() {

        // Acessar o custo do serviço
        val stringIntTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringIntTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }
        // Acessar a porcentagem da gorjeta
        //var selectedId = binding.tipOptions.checkedRadioButtonId

        val tipPorcentege = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // Calcular a gorjeta e arredondar para cima
        var tip = tipPorcentege * cost
        //val roundUp = binding.roundUpSwitch.isChecked
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }

        // formatar a gorjeta
        NumberFormat.getCurrencyInstance()
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        // Exibir a forjeta
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    // função handleKeyEvent() serve para ocultar o teclado quando a tecla Enter for precionada
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}
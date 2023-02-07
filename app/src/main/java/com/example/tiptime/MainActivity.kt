package com.example.tiptime

import android.os.Bundle
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
    }

    // Calcular a gorjeta
    private fun calculateTip() {

        // Acessar o custo do serviço
        val stringIntTextField = binding.costOfService.text.toString()
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
}
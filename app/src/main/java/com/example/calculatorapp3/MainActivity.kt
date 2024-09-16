package com.example.calculatorapp3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import com.example.calculatorapp3.R.*

class MainActivity : AppCompatActivity() {

    // Variabel untuk logika kalkulator
    private lateinit var tvInput: TextView
    private var currentInput: String = ""
    private var operator: Char? = null
    private var firstValue: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable Edge to Edge
        enableEdgeToEdge()

        // Set layout
        setContentView(layout.activity_main)

        // Mengatur padding berdasarkan window insets (Edge to Edge handling)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Menghubungkan TextView input dari layout
        tvInput = findViewById(id.tvInput)

        // Atur tombol dan logika kalkulator
        setUpButtonListeners()
    }

    private fun setUpButtonListeners() {
        val buttons = listOf(
            id.btn0, id.btn1, id.btn2, id.btn3, id.btn4,
            id.btn5, id.btn6, id.btn7, id.btn8, id.btn9
        )

        // Mengatur aksi tombol angka
        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                appendNumber((it as Button).text.toString())
            }
        }

        // Mengatur aksi tombol operator
        findViewById<Button>(id.btnPlus).setOnClickListener { setOperator('+') }
        findViewById<Button>(id.btnMinus).setOnClickListener { setOperator('-') }
        findViewById<Button>(id.btnMultiply).setOnClickListener { setOperator('*') }
        findViewById<Button>(id.btnDivide).setOnClickListener { setOperator('/') }

        // Mengatur aksi tombol sama dengan (=) dan clear (C)
        findViewById<Button>(id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(id.btnClear).setOnClickListener { clear() }
    }

    private fun appendNumber(number: String) {
        currentInput += number
        // Menampilkan input angka saat ini bersama operator jika sudah dipilih
        if (operator != null) {
            tvInput.text = "${firstValue?.toString() ?: ""} $operator $currentInput"
        } else {
            tvInput.text = currentInput
        }
    }

    private fun setOperator(op: Char) {
        if (currentInput.isNotEmpty()) {
            firstValue = currentInput.toDouble()
            operator = op
            currentInput = ""
            // Menampilkan angka pertama dan operator saat dipilih
            tvInput.text = "$firstValue $operator"
        }
    }

    private fun calculateResult() {
        if (firstValue != null && operator != null && currentInput.isNotEmpty()) {
            val secondValue = currentInput.toDouble()
            val result = when (operator) {
                '+' -> firstValue!! + secondValue
                '-' -> firstValue!! - secondValue
                '*' -> firstValue!! * secondValue
                '/' -> firstValue!! / secondValue
                else -> 0.0
            }

            // Menampilkan hasil perhitungan
            tvInput.text = "$firstValue $operator $secondValue = $result"
            currentInput = result.toString()
            firstValue = null
            operator = null
        }
    }

    private fun clear() {
        currentInput = ""
        firstValue = null
        operator = null
        tvInput.text = "0"
    }
}

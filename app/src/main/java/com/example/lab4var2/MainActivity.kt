package com.example.lab4var2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab4var2.ui.theme.Lab4var2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4var2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Введите x (например, 2.0)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val x = input.toDoubleOrNull()
            result = if (x == null || x == 0.0) {
                "Введите корректное число x"
            } else {
                val eps = 0.0001
                var sum = 0.0
                var term = 1.0
                var n = 1
                var sign = 1
                var iterations = 0

                while (true) {
                    val power = 2 * n - 1
                    term = sign * 1.0 / (power * Math.pow(x, power.toDouble()))
                    if (Math.abs(term) < eps) break
                    sum += term
                    sign = -sign
                    n++
                    iterations++
                }

                buildString {
                    appendLine("Сумма:")
                    appendLine(sum)
                    appendLine()
                    appendLine("Последнее слагаемое:")
                    appendLine(term)
                    appendLine()
                    append("Итераций: $iterations")
                }
            }
        }) {
            Text("Вычислить")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = result,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DemoScreenPreview() {
    Lab4var2Theme {
        DemoScreen()
    }
}
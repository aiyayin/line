package com.line.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

class TestComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConstraintLayout(
                modifier = Modifier
                    .background(Color.Cyan)
                    .padding(20.dp)
            ) {
                PreviewGreeting()
            }
        }

    }

    @Preview
    @Composable
    fun PreviewGreeting() {
        Greeting("Android")
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!", modifier = Modifier
            .background(Color.Gray)
            .padding(40.dp))
    }
}
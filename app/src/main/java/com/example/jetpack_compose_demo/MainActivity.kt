package com.example.jetpack_compose_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.jetpack_compose_demo.data.local.database.AppDatabase
import com.example.jetpack_compose_demo.data.repository.TodoRepositoryImpl
import com.example.jetpack_compose_demo.feature.TodoNav
import com.example.jetpack_compose_demo.feature.TodoScreen
import com.example.jetpack_compose_demo.feature.TodoViewModel
import com.example.jetpack_compose_demo.ui.theme.JetpackcomposedemoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.jvm.java


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            JetpackcomposedemoTheme {
                TodoNav()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier:  Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackcomposedemoTheme {
        Greeting("Android")
    }
}
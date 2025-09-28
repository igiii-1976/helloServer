package com.example.android_helloworld

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
import com.example.android_helloworld.ui.theme.Android_helloworldTheme
import java.io.IOException
import android.util.Log

class MainActivity : ComponentActivity() {

    // reference to our server
    private var server: helloServer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Start the server here
        server = helloServer(8080)
        try {
            server?.start()
            Log.i("MainActivity", "Server started on port 8080")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        setContent {
            Android_helloworldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        server?.stop()
        Log.i("MainActivity", "Server stopped")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Android_helloworldTheme {
        Greeting("Android")
    }
}
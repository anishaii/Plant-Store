package com.example.plantstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantstore.ui.theme.PlantStoreTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashBody()

        }
    }
}

@Composable
fun SplashBody(){
    Scaffold {
        innerpadding ->

        Column(modifier = Modifier
            .padding(innerpadding).background(Color.White)
            .fillMaxSize()

        ) {

        }

    }

}


@Preview
@Composable
fun PreviewSplash(){
    SplashBody()
}




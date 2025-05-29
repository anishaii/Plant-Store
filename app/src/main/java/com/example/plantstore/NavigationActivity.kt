package com.example.plantstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantstore.ui.theme.PlantStoreTheme

class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationBody()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBody(){
    data class BottomNavItem(val label: String, val icon: ImageVector)

    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Search", Icons.Default.Search),
        BottomNavItem("Profile", Icons.Default.Person)
    )

    var selectedIndex by remember{ mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ecommerce")

                },
                actions = {
                    IconButton( onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null

                        )
                    }

                    IconButton( onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null

                        )
                    }

                },


//                navigationIcon = {
//                    IconButton( onClick = {}) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = null
//
//                        )
//                    }
//
//
//                }
            )
        }
    ){
        padding ->
        Column(modifier = Modifier
            .padding(padding).background(Color.White)
            .fillMaxSize()
        ){

        }
    }
}


@Preview(showBackground = true)
@Composable

fun PreviewNavigation(){
    NavigationBody()
}

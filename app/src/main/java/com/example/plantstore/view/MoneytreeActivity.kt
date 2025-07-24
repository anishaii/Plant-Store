package com.example.plantstore.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantstore.R
import com.example.plantstore.view.ui.theme.PlantStoreTheme

class MoneytreeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Get product data from intent
        val productName = intent.getStringExtra("productName") ?: "Unknown Plant"
        val productPrice = intent.getStringExtra("productPrice") ?: "NPR. 0"
        val productImageRes = intent.getIntExtra("productImageRes", R.drawable.orchid)

        setContent {
            PlantStoreTheme {
                ProductDetailScreen(
                    name = productName,
                    price = productPrice,
                    imageRes = productImageRes
                )
            }
        }
    }
}

@Composable
fun ProductDetailScreen(name: String, price: String, imageRes: Int) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = price,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { /* Add to cart logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Add to cart", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            BenefitsText()
        }
    }
}

@Composable
fun BenefitsText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFDF5F0), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Benefits of the Money Tree",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Unique, Sculptural Design",
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Its lush foliage create a bold, eye-catching focal point," +
                    " perfect for entryways or modern interiors."
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Low Maintenance Appeal",
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "With moderate care, it stays healthy and vibrant, ideal for those seeking " +
                    "an impressive plant with minimal fuss."
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Calming Effect",
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Its rich green leaves and structured form create a soothing, natural" +
                    " focal point that can help ease tension in busy spaces."
        )
    }
}

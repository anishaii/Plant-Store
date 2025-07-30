package com.example.plantstore.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantstore.R
import com.example.plantstore.repository.UserRepositoryImpl
import com.example.plantstore.viewmodel.UserViewModel
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : ComponentActivity() {

    private val viewModel by viewModels<UserViewModel> {
        @Suppress("UNCHECKED_CAST")
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(UserRepositoryImpl()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = viewModel.getCurrentUser()
        currentUser?.uid?.let {
            viewModel.getUserById(it)
        }

        setContent {
            ProfileScreen(viewModel)
        }
    }
}

@Composable
fun ProfileScreen(viewModel: UserViewModel) {
    val user by viewModel.users.observeAsState()
    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val softGreen = Color(0xFF81C784) // Subtle green

    LaunchedEffect(user) {
        user?.let {
            firstName = it.firstName
            lastName = it.lastName
            phone = it.phone
            address = it.address
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Profile image
            Image(
                painter = painterResource(id = R.drawable.people),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .padding(4.dp)
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val uid = viewModel.getCurrentUser()?.uid
                    if (uid != null) {
                        val updateMap = mutableMapOf<String, Any?>(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "phone" to phone,
                            "address" to address
                        )
                        viewModel.updateProfile(uid, updateMap) { success, message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = softGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Profile", color = Color.White)
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.logout { success, message ->
                        if (success) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = softGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout", color = Color.White)
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val firebaseUser = viewModel.getCurrentUser()
                    val uid = firebaseUser?.uid
                    if (firebaseUser != null && uid != null) {
                        firebaseUser.delete().addOnCompleteListener { deleteTask ->
                            if (deleteTask.isSuccessful) {
                                val database = FirebaseDatabase.getInstance()
                                val userRef = database.reference.child("users").child(uid)

                                userRef.removeValue().addOnCompleteListener {
                                    Toast.makeText(
                                        context,
                                        "Account deleted",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent =
                                        Intent(context, LoginActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Failed to delete account",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete Account", color = Color.White)
            }
        }
    }
}

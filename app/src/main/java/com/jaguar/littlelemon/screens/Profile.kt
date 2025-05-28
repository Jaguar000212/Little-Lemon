package com.jaguar.littlelemon.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jaguar.littlelemon.models.User


@Composable
fun Profile(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userState = remember { mutableStateOf<User?>(null) }

    LaunchedEffect(currentUser?.uid) {
        if (currentUser != null) {
            FirebaseFirestore.getInstance().collection("users").document(currentUser.uid)
                .get().addOnSuccessListener { document ->
                    try {
                        userState.value = document.toObject(User::class.java)
                    } catch (e: Exception) {
                        Log.e("Firestore", "Deserialization failed: ${e.localizedMessage}")
                    }

                }.addOnFailureListener { exception ->
                    Log.e("Firestore", "Error fetching User: ${exception.localizedMessage}")
                }
        } else {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
    val user = userState.value ?: User()
    Column(modifier = modifier) {
        Text(text = "Name: ${user.firstName} ${user.lastName}", modifier = Modifier.fillMaxWidth())
        Text(text = "Email: ${user.email}", modifier = Modifier.fillMaxWidth())
        Text(
            text = "Non-Vegetarian: ${if (user.nonVeg) "Yes" else "No"}",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
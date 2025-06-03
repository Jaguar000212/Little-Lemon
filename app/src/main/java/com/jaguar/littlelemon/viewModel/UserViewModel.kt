package com.jaguar.littlelemon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jaguar.littlelemon.exceptions.UserNotLoggedInException
import com.jaguar.littlelemon.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            if (currentUser == null) {
                _user.value = null
                return@launch
            }
            FirebaseFirestore.getInstance().collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    val user = User(
                        firstName = document.getString("firstName") ?: "",
                        lastName = document.getString("lastName") ?: "",
                        email = document.getString("email") ?: "",
                        nonVeg = document.getBoolean("nonVeg") ?: false,
                        favorites = document.get("favorites") as? List<String> ?: emptyList()
                    )
                    _user.value = user
                }
                .addOnFailureListener {
                    _user.value = null
                    throw UserNotLoggedInException("User not logged in or data fetch failed.")
                }
        }
    }
}
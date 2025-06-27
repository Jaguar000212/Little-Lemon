package com.jaguar.littlelemon.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.jaguar.littlelemon.exceptions.UserNotLoggedInException
import com.jaguar.littlelemon.models.Configs
import com.jaguar.littlelemon.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _isAdmin = MutableStateFlow(false)

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val currentUser = getFirebaseUser()
            if (currentUser == null) {
                _user.value = null
                return@launch
            }
            fetchUserData()
        }
    }

    private fun getFirebaseUser(): FirebaseUser? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser
    }

    fun fetchUserData() {
        viewModelScope.launch {
            val currentUser = getFirebaseUser()
            if (currentUser != null) {
                FirebaseFirestore.getInstance().collection("users").document(currentUser.uid).get()
                    .addOnSuccessListener { document ->
                        val user = User(
                            name = document.getString("name") ?: "",
                            email = currentUser.email ?: "",
                            phone = document.getString("phone") ?: "",
                            nonVeg = document.getBoolean("nonVeg") ?: false,
                            favorites = document.get("favorites") as? List<String> ?: emptyList()
                        )
                        _user.value = user
                    }.addOnFailureListener {
                        _user.value = null
                        throw UserNotLoggedInException("User not logged in or data fetch failed.")
                    }
            }
            if (currentUser?.email == Configs.admin.value) _isAdmin.value = true
            else _isAdmin.value = false
        }
    }

    fun isProfileComplete(): Boolean {
        val user = _user.value ?: return false
        return user.getName().isNotEmpty()
    }

    fun logIn(email: String, password: String): Task<Void> {
        val auth = FirebaseAuth.getInstance()
        return auth.signInWithEmailAndPassword(email, password).continueWithTask { task ->
            if (task.isSuccessful) {
                fetchUser()
                Tasks.forResult(null)
            } else {
                Tasks.forException(
                    task.exception ?: UserNotLoggedInException("Login failed.")
                )
            }
        }
    }

    fun logOut() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        _user.value?.clearUserData() // Reset user data
        _user.value = null
    }

    fun register(
        email: String, password: String
    ): Task<AuthResult> {
        val auth = FirebaseAuth.getInstance()
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun forgotPassword(email: String): Task<Void> {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        return auth.sendPasswordResetEmail(email)
    }

    fun updateData(
        name: String = this._user.value?.getName() ?: "",
        email: String = this._user.value?.getEmail() ?: "",
        phone: String = this._user.value?.getPhone() ?: "",
        nonVeg: Boolean = this._user.value?.isNonVeg() ?: false,
        favorites: List<String> = this._user.value?.getFavorites() ?: emptyList()
    ): Task<Void> {
        val userMap = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "nonVeg" to nonVeg,
            "favorites" to favorites
        )
        val currentUser = getFirebaseUser() ?: return Tasks.forException(UserNotLoggedInException())

        return FirebaseFirestore.getInstance().collection("users").document(currentUser.uid)
            .set(userMap)
    }

    fun checkIfLoggedIn() {
        if (_user.value == null) {
            throw UserNotLoggedInException("User not logged in or data fetch failed.")
        }
    }

    fun checkIfAdmin(): Boolean {
        return _isAdmin.value
    }
}
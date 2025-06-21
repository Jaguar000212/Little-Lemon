package com.jaguar.littlelemon.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.jaguar.littlelemon.models.Dish
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DishesViewModel : ViewModel() {
    private val _dishes = MutableStateFlow<List<Dish>>(emptyList())
    val dishes: StateFlow<List<Dish>> = _dishes
    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        fetchDishes()
    }

    fun fetchDishes() {
        viewModelScope.launch {
            isRefreshing.value = true
            FirebaseFirestore.getInstance().collection("dishes").get()
                .addOnSuccessListener { result ->
                    val fetchedDishes = mutableListOf<Dish>()
                    for (document in result) {
                        try {
                            val dish = document.toObject(Dish::class.java)
                            fetchedDishes.add(dish)
                        } catch (_: Exception) {
                        }
                    }
                    _dishes.value = fetchedDishes
                }.addOnFailureListener { exception ->
                    Log.e("Firestore", "Error fetching dishes: ${exception.localizedMessage}")
                }
            delay(1_000L)
            isRefreshing.value = false
        }
    }
}

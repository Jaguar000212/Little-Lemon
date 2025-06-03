package com.jaguar.littlelemon.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.jaguar.littlelemon.models.Dish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DishesViewModel : ViewModel() {
    private val _dishes = MutableStateFlow<List<Dish>>(emptyList())
    val dishes: StateFlow<List<Dish>> = _dishes

    init {
        fetchDishes()
    }

    private fun fetchDishes() {
        FirebaseFirestore.getInstance().collection("dishes").get().addOnSuccessListener { result ->
            val fetchedDishes = mutableListOf<Dish>()
            for (document in result) {
                try {
                    val dish = document.toObject(Dish::class.java)
                    fetchedDishes.add(dish)
                } catch (e: Exception) {
                    Log.e("Firestore", "Deserialization failed: ${e.localizedMessage}")
                }
            }
            _dishes.value = fetchedDishes
        }.addOnFailureListener { exception ->
            Log.e("Firestore", "Error fetching dishes: ${exception.localizedMessage}")
        }
    }
}

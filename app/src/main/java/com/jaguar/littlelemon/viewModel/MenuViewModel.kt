package com.jaguar.littlelemon.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jaguar.littlelemon.models.Dish
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.UUID

class MenuViewModel : ViewModel() {
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
                            val dish = document.toObject(Dish::class.java).copy(id = document.id)
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

    fun deleteDish(dish: Dish) {
        val dishId = dish.getId()
        if (dishId.isBlank()) {
            Log.e("Firestore", "Dish ID is null or blank. Cannot delete.")
            return
        }
        viewModelScope.launch {
            FirebaseFirestore.getInstance().collection("dishes").document(dish.getId()).delete()
                .addOnSuccessListener {
                    Log.d("Firestore", "Dish deleted successfully")
                    fetchDishes()
                }.addOnFailureListener { exception ->
                    Log.e("Firestore", "Error deleting dish: ${exception.localizedMessage}")
                }
        }
    }

    fun updateDish(context: Context, dish: Dish) {
        val db = FirebaseFirestore.getInstance()
        val storageRef = FirebaseStorage.getInstance().reference
        val docRef = db.collection("dishes").document(dish.getId())

        if (dish.getImageURL().isNotEmpty() && dish.getImageURL().startsWith("content://")) {
            val imageUri = dish.getImageURL().toUri()

            try {
                val bitmap = ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        context.contentResolver, imageUri
                    )
                )

                // Compress the bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 70, baos)
                val compressedData = baos.toByteArray()

                val imageRef = storageRef.child("dish_images/${dish.getId()}.webp")
                imageRef.putBytes(compressedData).addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { url ->
                        docRef.set(
                            dish.copy(
                                imageURL = url.toString()
                            )
                        ).addOnSuccessListener {
                            Log.d("Firestore", "Dish updated successfully")
                            fetchDishes()
                        }.addOnFailureListener { exception ->
                            Log.e(
                                "Firestore",
                                "Error updating dish: ${exception.localizedMessage}"
                            )
                        }
                    }
                }.addOnFailureListener {
                    Log.e("Firestore", "Image upload failed: ${it.localizedMessage}")
                }

            } catch (e: Exception) {
                Log.e("Firestore", "Image processing failed: ${e.localizedMessage}")
            }

        } else {
            docRef.set(dish).addOnSuccessListener {
                Log.d("Firestore", "Dish updated successfully")
                fetchDishes()
            }.addOnFailureListener { exception ->
                Log.e("Firestore", "Error updating dish: ${exception.localizedMessage}")
            }
        }
    }

    fun addDish(context: Context, dish: Dish) {
        val id = UUID.randomUUID()
        val db = FirebaseFirestore.getInstance()
        val storageRef = FirebaseStorage.getInstance().reference

        if (dish.getImageURL().isNotEmpty() && dish.getImageURL().startsWith("content://")) {
            val imageUri = dish.getImageURL().toUri()

            try {
                val bitmap = ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        context.contentResolver, imageUri
                    )
                )

                // Compress the bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 70, baos)
                val compressedData = baos.toByteArray()

                val imageRef = storageRef.child("dish_images/${id}.webp")
                imageRef.putBytes(compressedData).addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { url ->
                        db.collection("dishes").add(
                            dish.copy(
                                id = id.toString(),
                                imageURL = url.toString()
                            )
                        ).addOnSuccessListener {
                            Log.d("Firestore", "Dish added successfully")
                            fetchDishes()
                        }.addOnFailureListener { exception ->
                            Log.e("Firestore", "Error adding dish: ${exception.localizedMessage}")
                        }
                    }
                }.addOnFailureListener {
                    Log.e("Firestore", "Image upload failed: ${it.localizedMessage}")
                }

            } catch (e: Exception) {
                Log.e("Firestore", "Image processing failed: ${e.localizedMessage}")
            }

        } else {
            db.collection("dishes").add(dish).addOnSuccessListener {
                Log.d("Firestore", "Dish added successfully")
                fetchDishes()
            }.addOnFailureListener { exception ->
                Log.e("Firestore", "Error adding dish: ${exception.localizedMessage}")
            }
        }
    }

}
package com.jaguar.littlelemon.models

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await


object Configs {
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> get() = _isReady
    private val _admin = MutableStateFlow("")
    val admin: StateFlow<String> get() = _admin

    private val _categories = MutableStateFlow<Map<String, List<String>>>(emptyMap())
    val categories: StateFlow<Map<String, List<String>>> get() = _categories

    private val _types = MutableStateFlow<List<String>>(emptyList())
    val types: StateFlow<List<String>> get() = _types

    suspend fun initConfigs() {
        val result = FirebaseFirestore.getInstance()
            .collection("restaurant")
            .get()
            .await()

        for (document in result) {
            when (document.id) {
                "configs" -> _admin.value = document.getString("admin") ?: ""
                "categories" -> {
                    val categoriesMap = document.data.mapValues { it.value as List<String> }
                    _categories.value = categoriesMap
                }

                "types" -> _types.value = document.get("types") as List<String>
            }
        }
        _isReady.value = true
    }
}

package com.jaguar.littlelemon.models

import com.google.firebase.firestore.FirebaseFirestore


object Configs {
    private var admin: String = ""
    private var categories: Map<String, List<String>> = mapOf()
    private var types: List<String> = emptyList()

    init {
        FirebaseFirestore.getInstance().collection("restaurant").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    when (document.id) {
                        "configs" -> admin = document.getString("admin") ?: ""
                        "categories" -> {
                            val categoriesMap = document.data.mapValues { it.value as List<String> }
                            categories = categoriesMap
                        }

                        "types" -> types = document.get("types") as List<String>
                    }
                }
            }.addOnFailureListener { exception ->
                throw Exception("Error fetching configs: ${exception.localizedMessage}")
            }

    }

    fun getAdmin() = admin
    fun getCategories() = categories
    fun getTypes() = types
}
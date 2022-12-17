package com.example.individualproject.models

data class Disease(
    val description: String,
    val diseaseName: String,
    val id: Int,
    val specialityId: Int,
    val symptomList: List<Symptom>
)
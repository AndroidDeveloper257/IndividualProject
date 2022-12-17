package com.example.individualproject.fragments.main

data class SymptomsChoice(
    val id: Int,
    val diseaseId: Int,
    val symptomName: String,
    var isSelected: Boolean
)
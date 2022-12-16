package com.example.individualproject.models

class Speciality {
    var id: Int = -1
    var title: String? = null

    constructor(
        id: Int,
        title: String?
    ) {
        this.id = id
        this.title = title
    }

    constructor()
}
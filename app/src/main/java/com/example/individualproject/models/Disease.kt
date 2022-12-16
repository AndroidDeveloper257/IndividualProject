package com.example.individualproject.models

class Disease {
    var id: Int = -1
    var diseaseName: String? = null
    var specialityId: Int = -1

    constructor(
        id: Int,
        diseaseName: String?,
        specialityId: Int
    ) {
        this.id = id
        this.diseaseName = diseaseName
        this.specialityId = specialityId
    }

    constructor()
}
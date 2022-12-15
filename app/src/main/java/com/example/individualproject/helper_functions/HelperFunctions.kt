package com.example.individualproject.helper_functions

fun leftPad(number: Int): String {
    return number.toString().padStart(2, '0')
}

fun phoneNumberToId(phoneNumber: String): String {
    return phoneNumber.substring(4)
}

fun idToPhoneNumber(id: String): String {
    return "+998$id"
}
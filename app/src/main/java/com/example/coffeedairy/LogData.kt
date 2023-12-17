package com.example.coffeedairy

import java.util.Calendar
import java.util.Date

// 파이어베이스
data class LogData(
    var date: Date? = Date(),
    var coffeeName: String? = null,
    var cafeName: String? = null,
    var rating: Float? = null,
    var imageName: String? = null,
    var diary: String? = null,
    var key: String? = null
    ){

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "date" to date,
            "coffeeName" to coffeeName,
            "cafeName" to cafeName,
            "rating" to rating,
            "imageName" to imageName,
            "diary" to diary
        )
    }
}
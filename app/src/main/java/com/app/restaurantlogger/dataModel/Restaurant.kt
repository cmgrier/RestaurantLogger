package com.app.restaurantlogger.dataModel

import com.app.restaurantlogger.database.Place
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.database.SimplePlace
import com.app.restaurantlogger.database.samplePlace0
import com.app.restaurantlogger.database.sampleReviewList

data class Restaurant(
    val place: Place,
    val reviews: List<Review>,
) {
    fun averageRating(): Float? {
        var ratingSum = 0f
        var totalRatings = 0
        return if (reviews.isNotEmpty()) {
            reviews.forEach {
                it.rating?.let { rating ->
                    totalRatings += 1
                    ratingSum += rating
                }
            }
            ratingSum / totalRatings
        } else {
            null
        }
    }
}

enum class Cuisine {
    Italian,
    Mexican,
    Greek,
    Indian,
    American,
    Fast,
    Fusion,
    Other,
}

fun String.toCuisine(): Cuisine {
    Cuisine.entries.forEach {
        if (it.name == this) {
            return it
        }
    }
    return Cuisine.Other
}

val sampleRestaurant = Restaurant(
    place = samplePlace0,
    reviews = sampleReviewList,
)

val sampleRestaurantList = listOf(
    sampleRestaurant,
    sampleRestaurant,
    sampleRestaurant,
    sampleRestaurant,
    sampleRestaurant,
    sampleRestaurant,
    sampleRestaurant,
    sampleRestaurant
)
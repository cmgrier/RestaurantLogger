package com.app.restaurantlogger.dataModel

import com.app.restaurantlogger.database.Place
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.database.samplePlace0
import com.app.restaurantlogger.database.sampleReviewList
import com.app.restaurantlogger.screens.home.ui.Filters
import com.app.restaurantlogger.screens.home.ui.SortMethod

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

fun List<Restaurant>.sort(sortMethod: SortMethod): List<Restaurant> =
    when (sortMethod) {
        SortMethod.Rating -> this.sortedByDescending { it.averageRating() }
        SortMethod.Name -> this.sortedByDescending { it.place.name }
    }

fun List<Restaurant>.filter(filters: Filters): List<Restaurant> =
    this.filter { restaurant ->
        val isValidRating =
            restaurant.averageRating()?.let { averageRating ->
                averageRating < filters.ratingUpperLimit && averageRating > filters.ratingLowerLimit
            } ?: true
        val isValidCuisine =
            filters.cuisine?.let { cuisine -> restaurant.place.cuisine == cuisine.name } ?: true
        val isValidReviewed =
            filters.reviewed?.let { reviewFilter -> restaurant.reviews.isNotEmpty() == reviewFilter }
                ?: true
        isValidCuisine && isValidRating && isValidReviewed
    }

enum class Cuisine {
    Italian,
    Mexican,
    Greek,
    Indian,
    American,
    Fast,
    Fusion,
    Breakfast,
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

val sampleRestaurant =
    Restaurant(
        place = samplePlace0,
        reviews = sampleReviewList,
    )

val sampleRestaurantList =
    listOf(
        sampleRestaurant,
        sampleRestaurant,
        sampleRestaurant,
        sampleRestaurant,
        sampleRestaurant,
        sampleRestaurant,
        sampleRestaurant,
        sampleRestaurant,
    )

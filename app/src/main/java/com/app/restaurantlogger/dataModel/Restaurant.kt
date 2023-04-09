package com.app.restaurantlogger.dataModel

data class Restaurant(
    val title: String,
    val cuisine: Cuisine,
    val logs: List<MealLog>
) {
    fun averageRating(): Float {
        var rating = 0f
        return if (logs.isNotEmpty()) {
            logs.forEach {
                rating += it.rating
            }
            rating / logs.count()
        } else {
            rating
        }
    }
}

enum class Cuisine {
    Italian, Mexican, Greek, Indian, American, FastFood, Fusion
}

val sampleRestaurant = Restaurant(
    "Mama's Meatballs",
    Cuisine.Italian,
    sampleMealLogList
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
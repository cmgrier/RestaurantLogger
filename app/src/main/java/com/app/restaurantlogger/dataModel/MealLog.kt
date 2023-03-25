package com.app.restaurantlogger.dataModel

data class MealLog(
    val headline: String,
    val details: String,
    val rating: Float
)

val sampleMealLog0 = MealLog(
    "Best Meatball's in town!",
    "The chicken parm was a soggy, but the meatballs were so good and fresh!",
    3.5f
)

val sampleMealLog1 = MealLog(
    "Server was rude",
    "Took forever to get seated and we barely saw our server. Food was cold when we got it",
    2.0f
)

val sampleMealLog2 = MealLog(
    "Mashed Potatoes to die for",
    "perfect roast chicken dinner with the best mashed potatoes ever. Also great cocktails!",
    5.0f
)

val sampleMealLogList = listOf(
    sampleMealLog0,
    sampleMealLog1,
    sampleMealLog2
)

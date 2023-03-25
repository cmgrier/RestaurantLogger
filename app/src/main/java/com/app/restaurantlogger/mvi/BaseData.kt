package com.app.restaurantlogger.mvi

interface BaseData {
    val name: String
}

data class ExampleData(
    val exampleDatum: Int
): BaseData {
    override val name: String = "Example Data"
}
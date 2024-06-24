package com.app.restaurantlogger.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.app.restaurantlogger.dataModel.Cuisine
import com.app.restaurantlogger.dataModel.Restaurant

@Entity
data class Place(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "address") override val address: String?,
    @ColumnInfo(name = "cuisine") override val cuisine: String?,
) : SimplePlace

interface SimplePlace {
    val name: String
    val address: String?
    val cuisine: String?

    fun toPlace(uid: Int): Place =
        Place(
            uid = uid,
            name = name,
            address = address,
            cuisine = cuisine,
        )
}

data class DataPlace(
    override val name: String,
    override val address: String?,
    override val cuisine: String?,
) : SimplePlace

fun Place.toRestaurant(reviews: List<Review> = emptyList()): Restaurant =
    Restaurant(
        place = this,
        reviews = reviews,
    )

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun getAll(): List<Place>

    @Query("SELECT * FROM place WHERE uid IN (:placeIds)")
    fun loadAllByIds(placeIds: IntArray): List<Place>

    @Query("SELECT * FROM place WHERE uid IN (:cuisine)")
    fun loadAllByCuisine(cuisine: String): List<Place>

    @Query("SELECT * FROM place WHERE name LIKE :name")
    fun findByName(name: String): List<Place>

    @Insert
    fun insertAll(vararg place: Place)

    @Update
    fun update(place: Place)

    @Delete
    fun delete(place: Place)
}

val samplePlace0 =
    Place(
        uid = 0,
        name = "Mama's Meatballs",
        address = "",
        cuisine = Cuisine.Italian.name,
    )

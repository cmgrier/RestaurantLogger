package com.app.restaurantlogger.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class Review(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "placeId") override val placeId: Int,
    @ColumnInfo(name = "rating") override val rating: Float?,
    @ColumnInfo(name = "headline") override val headline: String,
    @ColumnInfo(name = "details") override val details: String?,
) : SimpleReview

interface SimpleReview {
    val placeId: Int
    val rating: Float?
    val headline: String
    val details: String?
}

data class DataReview(
    override val placeId: Int,
    override val headline: String,
    override val details: String?,
    override val rating: Float?,
) : SimpleReview

fun nearestHalfRating(rating: Float): Float {
    val remainder = if (rating > 1f) rating % rating.toInt() else rating
    return rating.toInt() +
        if (remainder > 0.25f) {
            if (remainder > .75f) {
                1f
            } else {
                .5f
            }
        } else {
            0f
        }
}

@Dao
interface ReviewDao {
    @Query("SELECT * FROM review")
    fun getAll(): List<Review>

    @Query("SELECT * FROM review WHERE uid IN (:reviewIds)")
    fun loadAllByIds(reviewIds: IntArray): List<Review>

    @Query("SELECT * FROM review WHERE placeId LIKE :placeId")
    fun findByPlace(placeId: Int): List<Review>

    @Insert
    fun insertAll(vararg reviews: Review)

    @Delete
    fun delete(review: Review)
}

val sampleReview0 =
    Review(
        uid = 0,
        placeId = 0,
        rating = 3.5f,
        headline = "Best Meatball's in town!",
        details = "The chicken parm was a soggy, but the meatballs were so good and fresh!",
    )

val sampleReview1 =
    Review(
        uid = 0,
        placeId = 0,
        rating = 2.0f,
        headline = "Server was rude",
        details = "Took forever to get seated and we barely saw our server. Food was cold when we got it",
    )

val sampleReview2 =
    Review(
        uid = 0,
        placeId = 0,
        rating = 5.0f,
        headline = "Mashed Potatoes to die for",
        details = "perfect roast chicken dinner with the best mashed potatoes ever. Also great cocktails!",
    )

val sampleReviewList =
    listOf(
        sampleReview0,
        sampleReview1,
        sampleReview2,
    )

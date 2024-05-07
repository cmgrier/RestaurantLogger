package com.app.restaurantlogger.log.mvi

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.app.restaurantlogger.AppScreen
import com.app.restaurantlogger.database.AppDatabase
import com.app.restaurantlogger.database.DataReview
import com.app.restaurantlogger.database.Review
import com.app.restaurantlogger.database.SimpleReview
import com.app.restaurantlogger.database.toRestaurant
import com.app.restaurantlogger.home.mvi.HomeViewModel
import com.app.restaurantlogger.mvi.BaseReducer
import com.app.restaurantlogger.mvi.BaseViewModel
import com.app.restaurantlogger.mvi.FetchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class LogViewModel @Inject constructor(
    private val appDatabase: AppDatabase,
) : BaseViewModel<LogState, LogIntent>() {
    private val reducer = LogReducer(LogState.initial())

    override val appScreen: AppScreen
        get() = AppScreen.Log

    override val state: StateFlow<LogState>
        get() = reducer.state

    override val floatingActionButtonAction: () -> Unit
        get() = {}

    override val floatingActionButton: @Composable () -> Unit
        get() = {}
    override val topBar: @Composable () -> Unit
        get() = {}

    init {
        viewModelScope.launch {
            resetScreen()
        }
    }

    fun loadData(placeId: Int) {
        thread {
            sendEvent(LogIntent.LoadingData)
            val reviews = appDatabase.reviewDao().findByPlace(placeId = placeId)
            val places = appDatabase.placeDao().loadAllByIds(intArrayOf(placeId))
            if (places.isEmpty()) {
                // Send Place Not Found error
            } else if (places.size > 1) {
                // Send Multiple Places Found error
            } else {
                val restaurant = places[0].toRestaurant(reviews = reviews)
                sendEvent(LogIntent.UpdateData(data = LogData(restaurant = restaurant, placeId = placeId)))
            }
        }
    }

    fun addReview(simpleReview: SimpleReview) {
        addReview(
            headline = simpleReview.headline,
            details = simpleReview.details,
            rating = simpleReview.rating,
        )
    }

    fun addReview(
        headline: String,
        details: String? = null,
        rating: Float? = null,
    ) {
        state.value.logData.placeId?.let { placeId ->
            val uid = DataReview(
                placeId = placeId,
                rating = rating,
                headline = headline,
                details = details,
            ).hashCode()

            val review = Review(
                uid = uid,
                placeId = placeId,
                rating = rating,
                headline = headline,
                details = details,
            )

            thread {
                sendEvent(LogIntent.LoadingData)
                appDatabase.reviewDao().insertAll(review)
                resetScreen()
            }
        }
    }

    private fun sendEvent(event: LogIntent) {
        reducer.sendEvent(event)
    }

    private fun resetScreen() {
        state.value.logData.placeId?.let { placeId ->
            loadData(placeId = placeId)
        }
    }

    class LogReducer(initialState: LogState): BaseReducer<LogState, LogIntent>(initialState) {
        override fun reduce(oldState: LogState, event: LogIntent) {
            when(event) {
                is LogIntent.LoadingData -> {
                    if (oldState.fetchStatus != FetchStatus.Fetching) {
                        setState(oldState.copy(fetchStatus = FetchStatus.Fetching))
                    }
                }
                is LogIntent.UpdateData -> {
                    setState(oldState.copy(fetchStatus = FetchStatus.Fetched, logData = event.data))
                }
            }
        }
    }
}
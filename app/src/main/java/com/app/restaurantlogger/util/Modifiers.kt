package com.app.restaurantlogger.util

import androidx.compose.ui.Modifier

fun Modifier.thenIf(
    condition: Boolean,
    ifFalse: (Modifier.() -> Modifier)? = null,
    ifTrue: Modifier.() -> Modifier,
): Modifier {
    return if (condition) {
        then(ifTrue(Modifier))
    } else if (ifFalse != null) {
        then(ifFalse(Modifier))
    } else {
        this
    }
}

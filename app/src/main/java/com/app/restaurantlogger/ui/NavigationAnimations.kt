package com.app.restaurantlogger.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry

private val slideSpeed: FiniteAnimationSpec<IntOffset> = tween(500)

private val scaleSpeed: FiniteAnimationSpec<Float> = tween(400)

private val scaleAmount = .85f

private val fadeSpeed: FiniteAnimationSpec<Float> = tween(500)

val enterSlideLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = slideSpeed,
    )
}

val enterSlideRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = slideSpeed,
    )
}

val exitSlideLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = slideSpeed,
    )
}

val exitSlideRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = slideSpeed,
    )
}

val enterScaleIn: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    scaleIn(animationSpec = scaleSpeed, initialScale = scaleAmount)
}

val exitScaleOut: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    scaleOut(animationSpec = scaleSpeed, targetScale = scaleAmount)
}

val enterFadeIn: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    fadeIn(animationSpec = fadeSpeed)
}

val exitFadeOut: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    fadeOut(animationSpec = fadeSpeed)
}

val enterZoomLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    enterSlideLeft() + enterScaleIn()
}

val enterZoomRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    enterSlideRight() + enterScaleIn()
}

val exitZoomLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    exitSlideLeft() + exitScaleOut()
}

val exitZoomRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    exitSlideRight() + exitScaleOut()
}

val enterShimmer: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    enterFadeIn() + enterScaleIn()
}

val exitShimmer: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    fadeOut() + exitScaleOut()
}

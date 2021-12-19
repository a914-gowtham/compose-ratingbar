package com.gowtham.ratingbar

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.gowtham.ratingbar.RatingBarUtils.stepSized
import kotlin.math.roundToInt

sealed class StepSize {
    object ONE : StepSize()
    object HALF : StepSize()
}

sealed class RatingBarStyle {
    object Normal : RatingBarStyle()
    object HighLighted : RatingBarStyle()
}

//For ui testing
val StarRatingKey = SemanticsPropertyKey<Float>("StarRating")
var SemanticsPropertyReceiver.starRating by StarRatingKey


/**
 * @param value is current selected rating count
 * @param numStars Sets the number of stars to show.
 * @param size for each star
 * @param padding for set padding to each star
 * @param isIndicator Whether this rating bar is only an indicator
 * @param activeColor A [Color] representing an active star (or part of it)
 * @param inactiveColor A [Color] representing a inactive star (or part of it)
 * @param stepSize Minimum value to trigger a change
 * @param ratingBarStyle Can be [RatingBarStyle.Normal] or [RatingBarStyle.HighLighted]
 * @param onRatingChanged A function to be called when the click or drag is released and rating value is passed
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    value: Float = 0f,
    numStars: Int = 5,
    size: Dp = 26.dp,
    padding: Dp = 2.dp,
    isIndicator: Boolean = false,
    activeColor: Color = Color(0xffffd740),
    inactiveColor: Color = Color(0xffffecb3),
    stepSize: StepSize = StepSize.ONE,
    hideInactiveStars: Boolean = false,
    ratingBarStyle: RatingBarStyle = RatingBarStyle.Normal,
    onValueChange: (Float) -> Unit,
    onRatingChanged: (Float) -> Unit
) {
    var rowSize by remember { mutableStateOf(Size.Zero) }
    var lastDraggedValue by remember { mutableStateOf(0f) }
    val direction = LocalLayoutDirection.current

    Row(modifier = modifier
        .onSizeChanged { rowSize = it.toSize() }
        .pointerInput(
            Unit
        ) {
            //handling dragging events
            detectHorizontalDragGestures(
                onDragEnd = {
                    if (isIndicator || hideInactiveStars)
                        return@detectHorizontalDragGestures
                    onRatingChanged(lastDraggedValue)
                },
                onDragCancel = {

                },
                onDragStart = {

                },
                onHorizontalDrag = { change, _ ->
                    if (isIndicator || hideInactiveStars)
                        return@detectHorizontalDragGestures
                    change.consumeAllChanges()
                    val x1 = change.position.x.coerceIn(0f, rowSize.width)
                    val calculatedStars =
                        RatingBarUtils.calculateStars(
                            x1, rowSize.width,
                            numStars, padding.value.toInt()
                        )
                    var newValue =
                        calculatedStars
                            .stepSized(stepSize)
                            .coerceIn(0f, numStars.toFloat())

                    if (direction == LayoutDirection.Rtl)
                        newValue = numStars - newValue
                    onValueChange(newValue)
                    lastDraggedValue = newValue
                }
            )
        }
        .pointerInteropFilter {
            if (isIndicator || hideInactiveStars)
                return@pointerInteropFilter false
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    //handling when click events
                    val calculatedStars =
                        RatingBarUtils.calculateStars(
                            it.x, rowSize.width,
                            numStars, padding.value.toInt()
                        )
                    var newValue =
                        calculatedStars
                            .stepSized(stepSize)
                            .coerceIn(0f, numStars.toFloat())
                    if (direction == LayoutDirection.Rtl)
                        newValue = numStars - newValue
                    onValueChange(newValue)
                    onRatingChanged(newValue)
                }
            }
            true
        }) {
        ComposeStars(
            value, numStars, size, padding, activeColor,
            inactiveColor, hideInactiveStars, ratingBarStyle
        )
    }
}

@Composable
fun ComposeStars(
    value: Float,
    numStars: Int,
    size: Dp,
    padding: Dp,
    activeColor: Color,
    inactiveColor: Color,
    hideInactiveStars: Boolean,
    ratingBarStyle: RatingBarStyle
) {

    val ratingPerStar = 1f
    var remainingRating = value

    Row(modifier = Modifier
        .semantics { starRating = value }) {
        for (i in 1..numStars) {
            val starRating = when {
                remainingRating == 0f -> {
                    0f
                }
                remainingRating >= ratingPerStar -> {
                    remainingRating -= ratingPerStar
                    1f
                }
                else -> {
                    val fraction = remainingRating / ratingPerStar
                    remainingRating = 0f
                    fraction
                }
            }
            if (hideInactiveStars && starRating == 0.0f)
                break
            RatingStar(
                fraction = starRating,
                modifier = Modifier
                    .padding(
                        start = if (i > 1) padding else 0.dp,
                        end = if (i < numStars) padding else 0.dp
                    )
                    .size(size = size)
                    .testTag("RatingStar"),
                activeColor,
                inactiveColor,
                ratingBarStyle
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    var rating by remember { mutableStateOf(3.3f) }
    RatingBar(value = rating, onValueChange = {
        rating = it
    }) {
        Log.d("TAG", "RatingBarPreview: $it")
    }
}
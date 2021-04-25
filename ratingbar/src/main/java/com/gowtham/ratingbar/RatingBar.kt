package com.gowtham.ratingbar

import android.util.Log
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBarUtils.stepSized
import kotlinx.coroutines.coroutineScope
import kotlin.math.roundToInt

sealed class StepSize {
    object ONE : StepSize()
    object HALF : StepSize()
}

sealed class RatingBarStyle {
    object Normal : RatingBarStyle()
    object HighLighted : RatingBarStyle()
}

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
 * @param onRatingChanged A function to be called when the value changes
 */
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
    ratingBarStyle: RatingBarStyle = RatingBarStyle.Normal,
    onRatingChanged: (Float) -> Unit
) {
    val offsetX = remember { mutableStateOf(0f) }
    var width by remember { mutableStateOf(0f) }
    var lastSelectedStarWidth by remember { mutableStateOf(0f) }

    Surface {
        Row(modifier = modifier
            .onSizeChanged { width = it.width.toFloat() }
            .pointerInput(Unit) {
                if (isIndicator)
                    return@pointerInput
                coroutineScope {
                    forEachGesture {
                        awaitPointerEventScope {
                            val down = awaitFirstDown()
                            val change =
                                awaitHorizontalTouchSlopOrCancellation(down.id) { change, over ->
                                    val originalX = offsetX.value
                                    val newValue =
                                        (originalX + over).coerceIn(0f, width - 50.dp.toPx())
                                    change.consumePositionChange()
                                    offsetX.value = newValue
                                }

                            if (change != null) {
                                horizontalDrag(change.id) {
                                    val originalX = offsetX.value
                                    if ((originalX + it.positionChange().x) < 0) {
                                        onRatingChanged(0f)
                                        return@horizontalDrag
                                    }
                                    val newValue = (originalX + it.positionChange().x)
                                        .coerceIn(0f, width - 2.dp.toPx())
                                    it.consumePositionChange()
                                    offsetX.value = newValue
                                    lastSelectedStarWidth = offsetX.value
                                    val calculatedStars =
                                        RatingBarUtils.calculateStars(
                                            lastSelectedStarWidth, width,
                                            numStars, padding.value.toInt()
                                        )
                                    onRatingChanged(calculatedStars.stepSized(stepSize))
                                }
                            } else {
                                lastSelectedStarWidth = down.position.x
                                val calculatedStars =
                                    RatingBarUtils.calculateStars(
                                        lastSelectedStarWidth, width,
                                        numStars, padding.value.toInt()
                                    )
                                onRatingChanged(calculatedStars.stepSized(stepSize))
                            }
                        }
                    }
                }
            }) {
            Log.d("TAG", "RatingBar: $value")
            ComposeStars(
                value, numStars, size, padding, activeColor,
                inactiveColor, ratingBarStyle
            )
        }
    }

}

@Composable
fun ComposeStars(
    value: Float,
    numStars: Int,
    size: Dp,
    padding: Dp,
    activeColor: Color,
    inactiveColor: Color, ratingBarStyle: RatingBarStyle
) {

    val ratingPerStar = 1f
    var remainingRating = value

    Row(modifier = Modifier.semantics { starRating = value }) {
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
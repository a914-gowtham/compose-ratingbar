package com.gowtham.ratingbar

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlin.math.roundToInt

object RatingBar {

/**
*  @return calculated stars count that should be selected
* */
    fun calculateStars(
        draggedWidth: Float, width: Float,
        numStars: Int, padding: Int
    ): Int {
        var overAllComposeWidth = width
        val spacerWidth = numStars * (2 * padding)
        //removing padding's width
        overAllComposeWidth -= spacerWidth
        return if (draggedWidth != 0f)
            ((draggedWidth / overAllComposeWidth) * numStars).roundToInt()
        else 0
    }

}

/**
 * @param value is current selected rating count
 * @param numStars Sets the number of stars to show.
 * @param size for each star
 * @param padding for set padding to each star
 * @param isIndicator Whether this rating bar is only an indicator
 * @param imgResId - custom icon from drawable resource
 */
@Composable
fun RatingBar(
    value: Int = 0,
    numStars: Int = 5, size: Dp = 34.dp, padding: Dp = 3.dp,
    isIndicator: Boolean = false, activeColor: Color = Color(0xffffd740),
    inactiveColor: Color = Color(0xffffecb3),
    stepSize: Float = 1f, onRatingChanged: (Int) -> Unit,
    @DrawableRes imgResId: Int=R.drawable.rounded_point_star
) {
    val offsetX = remember { mutableStateOf(0f) }
    var width by remember { mutableStateOf(0f) }
    var lastSelectedStarWidth by remember { mutableStateOf(0f) }

    Surface {
        Row(modifier = Modifier
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
                                    val newValue = (originalX + it.positionChange().x)
                                        .coerceIn(0f, width - 2.dp.toPx())
                                    it.consumePositionChange()
                                    offsetX.value = newValue
                                    lastSelectedStarWidth = offsetX.value
                                    val calculatedStars =
                                        RatingBar.calculateStars(
                                            lastSelectedStarWidth, width,
                                            numStars, padding.value.toInt()
                                        )
                                    onRatingChanged(calculatedStars)
                                }
                            } else {
                                lastSelectedStarWidth = down.position.x
                                val calculatedStars =
                                    RatingBar.calculateStars(
                                        lastSelectedStarWidth, width,
                                        numStars, padding.value.toInt()
                                    )
                                Log.d("TAG", "Selected value: $width")
                                onRatingChanged(calculatedStars)
                            }
                        }
                    }
                }
            }) {
            ComposeStars(
                value, numStars, size, padding, activeColor,
                inactiveColor,imgResId
            )
        }
    }

}

@Composable
fun ComposeStars(
    value: Int,
    numStars: Int,
    size: Dp,
    padding: Dp,
    activeColor: Color,
    inactiveColor: Color,
    @DrawableRes imgResId: Int
) {
    Log.d("TAG", "lastSelectStarWidth: $value")
    for (i in 0 until numStars) {
        Icon(
            painter = painterResource(id = imgResId),
            contentDescription = null,
            modifier = Modifier
                .requiredSize(size)
                .padding(all = padding),
            tint = if (value != 0 && i < value) activeColor
            else inactiveColor
        )
    }
}
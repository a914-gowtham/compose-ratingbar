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
import androidx.compose.foundation.layout.size
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

sealed class StepSize{
    object ONE : StepSize()
    object HALF : StepSize()
}

sealed class RatingBarStyle{
    object Normal : RatingBarStyle()
    object HighLighted : RatingBarStyle()
}


fun Float.stepSized(stepSize: StepSize): Float{
   return if(stepSize is StepSize.ONE)
        this.roundToInt().toFloat()
    else{
        var value=this.toInt().toFloat()
         if(this<value.plus(0.5)) {
             value=value.plus(0.5).toFloat()
             value
         }
       else
           this.roundToInt().toFloat()
    }
}

object RatingBar {

/**
*  @return calculated stars count that should be selected
* */
    fun calculateStars(
        draggedWidth: Float, width: Float,
        numStars: Int, padding: Int
    ): Float {
        var overAllComposeWidth = width
        val spacerWidth = numStars * (2 * padding)
    Log.d("TAG", "overAllComposeWidth: $overAllComposeWidth")
    Log.d("TAG", "spacerWidth: $spacerWidth")
    Log.d("TAG", "padding: $padding")
    Log.d("TAG", "draggedWidth: $draggedWidth")

    //removing padding's width
        overAllComposeWidth -= spacerWidth
    Log.d("TAG", "overAllComposeWidth--: $overAllComposeWidth")
    return if (draggedWidth != 0f)
            ((draggedWidth / overAllComposeWidth) * numStars)
        else 0f
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
    value: Float = 0f,
    numStars: Int = 5, size: Dp = 26.dp, padding: Dp = 2.dp,
    isIndicator: Boolean = false, activeColor: Color = Color(0xffffd740),
    inactiveColor: Color = Color(0xffffecb3),
    stepSize: StepSize = StepSize.ONE, onRatingChanged: (Float) -> Unit,
    ratingBarStyle: RatingBarStyle=RatingBarStyle.Normal
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
                                    if((originalX + it.positionChange().x)<0){
                                        onRatingChanged(0f)
                                        return@horizontalDrag
                                    }
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
                                    onRatingChanged(calculatedStars.stepSized(stepSize))
                                }
                            } else {
                                lastSelectedStarWidth = down.position.x
                                val calculatedStars =
                                    RatingBar.calculateStars(
                                        lastSelectedStarWidth, width,
                                        numStars, padding.value.toInt()
                                    )
                                onRatingChanged(calculatedStars.stepSized(stepSize))
                            }
                        }
                    }
                }
            }) {
            ComposeStars(
                value, numStars, size, padding, activeColor,
                inactiveColor,ratingBarStyle
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
    inactiveColor: Color,ratingBarStyle: RatingBarStyle) {

    val ratingPerStar = 1f
    var remainingRating = value

    for (i in 1 .. numStars) {
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
                .padding(all = padding)
                .size(size = size),
            activeColor,
            inactiveColor,
            ratingBarStyle
        )
    }
}
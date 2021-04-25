package com.gowtham.ratingbar

import android.util.Log
import kotlin.math.roundToInt

object RatingBarUtils {

    /**
     *  @return selected or dragged stars
     *  float value that should be selected
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


    /**
     *  @return rating stars float value depends on [StepSize]
     *
     *  case 1: if selected star value is 3.234345 and [StepSize] is StepSize.ONE,
     *  this function will return 3.0.
     *  more ex: [this] 3.634345, return value would be 3.
     *  [this] 4.96367, return value would be 4.
     *
     *  case 2: if selected star value is 3.234345 and [StepSize] is StepSize.HALF,
     *  this function will return 3.5. So, 3.5 stars to be selected.
     *  more ex: [this] 3.634345, return value would be 4.
     *
     * */
    fun Float.stepSized(stepSize: StepSize): Float {
        return if (stepSize is StepSize.ONE)
            this.roundToInt().toFloat()
        else {
            var value = this.toInt().toFloat()
            if (this < value.plus(0.5)) {
                value = value.plus(0.5).toFloat()
                value
            } else
                this.roundToInt().toFloat()
        }
    }
}
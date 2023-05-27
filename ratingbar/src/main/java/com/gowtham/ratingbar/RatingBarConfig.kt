package com.gowtham.ratingbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * This is a helper class with the different configurations
 * required to be passed to the RatingBar composable.
 * ```kotlin
 * RatingBar(
 *      config = RatingBarConfig()
 *          .size(26.dp)
 *          .padding(2.dp)
 *          .style(RatingBarStyle.Normal)
 *          .numStars(5)
 *          .isIndicator(false)
 *          .activeColor(Color(0xffffd740))
 *          .inactiveColor(Color(0xffffecb3))
 *          .stepSize(StepSize.ONE)
 *          .hideInactiveStars(false),
 *      onValueChanged = {/* code here */},
 *     onRatingChanged = {/* code here */}
 * )
 * ```
 * @see [RatingBarStyle]
 */
class RatingBarConfig {

    /**
     * size for each star.
     */
    var size: Dp = 26.dp
        private set

    /**
     * stroke width for each star
     */
    var strokeWidth: Float = 1f
        private set

    /**
     * padding between each star.
     */
    var horizontalPadding: Dp = 2.dp
        private set

    /**
     * Can be [RatingBarStyle.Normal] or [RatingBarStyle.HighLighted]
     */
    var style: RatingBarStyle = RatingBarStyle.Normal
        private set

    /**
     * numStars Sets the number of stars to show.
     */
    var numStars: Int = 5
        private set

    /**
     * isIndicator Whether this rating bar is only an indicator.
     */
    var isIndicator: Boolean = false
        private set

    /**
     * A [Color] representing an active star (or part of it).
     */
    var activeColor: Color = Color(0xffffd740)
        private set

    /**
     * A [Color] representing a inactive star (or part of it).
     */
    var inactiveColor: Color = Color(0xffffecb3)
        private set

    /**
     * A border [Color] shown on inactive star.
     */
    var inactiveBorderColor: Color = Color(0xFF888888)
        private set

    /**
     * Minimum value to trigger a change
     */
    var stepSize: StepSize = StepSize.ONE
        private set

    /**
     * Whether the inactive stars should be hidden.
     */
    var hideInactiveStars: Boolean = false
        private set

    /**
     * Sets the size of the star.
     * @param value the value in Dp
     */
    fun size(value: Dp): RatingBarConfig =
        apply {
            size = value
        }

    /**
     * Sets the stroke width of stars
     * @param value the value in Float
     */
    fun strokeWidth(value: Float): RatingBarConfig =
        apply {
            strokeWidth = value
        }

    /**
     * Sets the padding between star.
     * @param value the value in Dp.
     */
    fun horizontalPadding(value: Dp): RatingBarConfig =
        apply {
            horizontalPadding = value
        }

    /**
     * Sets the style of the composable.
     * @param value the value in Float.
     * @see [RatingBarStyle]
     */
    fun style(value: RatingBarStyle): RatingBarConfig =
        apply {
            style = value
        }

    /**
     * Sets whether this rating bar is only an indicator.
     * @param value the value in Boolean.
     */
    fun isIndicator(value: Boolean): RatingBarConfig =
        apply {
            isIndicator = value
        }

    /**
     * Sets the number of stars to show.
     * @param value the value in Int.
     */
    fun numStars(value: Int): RatingBarConfig =
        apply {
            numStars = value
        }

    /**
     * Sets the [Color] representing an active star (or part of it).
     * @param value the value in [Color]
     */
    fun activeColor(value: Color): RatingBarConfig =
        apply {
            activeColor = value
        }

    /**
     * Sets the [Color] representing a inactive star (or part of it).
     * @param value the value in [Color]
     */
    fun inactiveColor(value: Color): RatingBarConfig =
        apply {
            inactiveColor = value
        }

    /**
     * Sets the [Color] representing a inactive star (or part of it).
     * @param value the value in [Color]
     */
    fun inactiveBorderColor(value: Color): RatingBarConfig =
        apply {
            inactiveBorderColor = value
        }

    /**
     * Sets the minimum value to trigger a change.
     * @param value the value in [StepSize]
     */
    fun stepSize(value: StepSize): RatingBarConfig =
        apply {
            stepSize = value
        }

    /**
     * Whether the inactive stars should be hidden.
     * @param value the value in Boolean
     */
    fun hideInactiveStars(value: Boolean): RatingBarConfig =
        apply {
            hideInactiveStars = value
        }
}
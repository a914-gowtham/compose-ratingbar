package com.gowtham.ratingbar

object RatingBarUtils {

    fun calculateStars(
        draggedX: Float,
        horizontalPaddingInPx: Float,
        starSizeInPx: Float,
        config: RatingBarConfig
    ): Float {

        if(draggedX<=0){
            return 0f
        }

        val starWidthWithRightPadding = starSizeInPx + (2 * horizontalPaddingInPx)
        val halfStarWidth = starSizeInPx / 2
        for (i in 1..config.numStars) {
            if (draggedX < (i * starWidthWithRightPadding)) {
                return if (config.stepSize is StepSize.ONE) {
                    i.toFloat()
                } else {
                    val crossedStarsWidth = (i - 1) * starWidthWithRightPadding
                    val remainingWidth = draggedX - crossedStarsWidth
                    if (remainingWidth <= halfStarWidth) {
                        i.toFloat().minus(0.5f)
                    } else {
                        i.toFloat()
                    }
                }
            }
        }
        return 0f
    }
}
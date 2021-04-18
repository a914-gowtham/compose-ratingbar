package com.gowtham.ratingbar

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.FractionalRectangleShape
import com.gowtham.ratingbar.addStar


private const val strokeWidth = 1f

@Composable
fun RatingStar(
    @FloatRange(from = 0.0, to = 1.0) fraction: Float,
    modifier: Modifier = Modifier,
    activeColor: Color,
    inactiveColor: Color,
    ratingBarStyle: RatingBarStyle) {
    Box(modifier = modifier) {
        FilledStar(fraction,activeColor)
        EmptyStar(fraction,ratingBarStyle,inactiveColor)
    }
}

@Composable
private fun FilledStar(fraction: Float,activeColor: Color) = Canvas(
    modifier = Modifier
        .fillMaxSize()
        .clip(FractionalRectangleShape(0f, fraction))
) {
    val path = Path().addStar(size)

    drawPath(path, color = activeColor, style = Fill) // Filled Star
    drawPath(path, color = activeColor, style = Stroke(width = strokeWidth)) // Border
}

@Composable
private fun EmptyStar(fraction: Float,ratingBarStyle: RatingBarStyle,inactiveColor: Color) = Canvas(
    modifier = Modifier
        .fillMaxSize()
        .clip(FractionalRectangleShape(fraction, 1f))
) {
    val path = Path().addStar(size)
    if(ratingBarStyle is RatingBarStyle.Normal)
        drawPath(path, color = inactiveColor, style = Fill) // Border
    else
       drawPath(path, color = Color.Gray, style = Stroke(width = strokeWidth)) // Border
}

@Preview(showBackground = true)
@Composable
fun EmptyRatingStarPreview() {
    RatingStar(fraction = 0f, modifier = Modifier.size(20.dp),
        Color(0xffffd740),
        Color(0xffffecb3),
        RatingBarStyle.Normal)
}

@Preview(showBackground = true)
@Composable
fun PartialRatingStarPreview() {
    RatingStar(fraction = 0.7f, modifier = Modifier.size(20.dp),
        Color(0xffffd740),
        Color(0xffffecb3),
        RatingBarStyle.Normal)
}

@Preview(showBackground = true)
@Composable
fun FullRatingStarPreview() {
    RatingStar(fraction = 1f, modifier = Modifier.size(20.dp),
        Color(0xffffd740),
        Color(0xffffecb3),
        RatingBarStyle.Normal)
}

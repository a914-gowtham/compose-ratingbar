package com.gowtham.compose_ratingbar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.compose_ratingbar.ui.theme.ComposeRatingBarTheme
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}


@Composable
fun MyApp() {
    var rating: Float by rememberSaveable { mutableStateOf(4f) }
    var highLightedRating: Float by rememberSaveable { mutableStateOf(4f) }
    ComposeRatingBarTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier =
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                RatingBar(value = rating, onRatingChanged = {
                    rating=it
                    Log.d("TAG", "onRatingChanged: $it")
                })
                RatingBar(value = highLightedRating, onRatingChanged = {
                    highLightedRating=it
                    Log.d("TAG", "onRatingChanged: $it")
                },ratingBarStyle = RatingBarStyle.HighLighted)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
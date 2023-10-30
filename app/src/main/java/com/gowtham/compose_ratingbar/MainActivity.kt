package com.gowtham.compose_ratingbar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.compose_ratingbar.MainActivity.Companion.initialRating
import com.gowtham.compose_ratingbar.ui.theme.JetpackComposeTheme
import com.gowtham.compose_ratingbar_multiplatform.RatingBar
import com.gowtham.compose_ratingbar_multiplatform.RatingBarStyle
import com.gowtham.compose_ratingbar_multiplatform.StepSize


class MainActivity : ComponentActivity() {

    companion object {
        var initialRating = 3.2f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}


@Composable
fun MyApp() {
    JetpackComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var ratingOne: Float by rememberSaveable { mutableFloatStateOf(1.4f) }
    var ratingTwo: Float by rememberSaveable { mutableFloatStateOf(initialRating) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        RatingBar(
            value = ratingOne,
            stepSize = StepSize.HALF,
            style = RatingBarStyle.Fill(),
            onValueChange = {
                ratingOne = it
            },
            onRatingChanged = {
                Log.d("TAG", "onRatingChanged: $it")
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        RatingBar(
            value = ratingTwo,
            painterEmpty = painterResource(id = R.drawable.icon_empty),
            painterFilled = painterResource(id = R.drawable.icon_filled),
            onValueChange = {
                ratingTwo = it
            },
            onRatingChanged = {
                Log.d("TAG", "onRatingChanged: $it")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
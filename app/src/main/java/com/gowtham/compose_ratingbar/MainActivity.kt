package com.gowtham.compose_ratingbar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.compose_ratingbar.MainActivity.Companion.initialRating
import com.gowtham.compose_ratingbar.ui.theme.ComposeRatingBarTheme
import com.gowtham.ratingbar.RatingBar

class MainActivity : ComponentActivity() {

    companion object{
        const val initialRating=3.2f
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
  ComposeRatingBarTheme {
        Surface(color = MaterialTheme.colors.background) {
           MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var rating: Float by rememberSaveable { mutableStateOf(initialRating) }

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
        Spacer(modifier = Modifier.requiredSize(4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
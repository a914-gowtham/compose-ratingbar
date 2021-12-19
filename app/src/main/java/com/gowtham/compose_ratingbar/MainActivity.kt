package com.gowtham.compose_ratingbar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.gowtham.compose_ratingbar.MainActivity.Companion.initialRating
import com.gowtham.compose_ratingbar.ui.theme.JetpackComposeTheme
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.instacart.library.truetime.TrueTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
    var rating: Float by rememberSaveable { mutableStateOf(2.8f) }

    Column(
        modifier =
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        RatingBar(value = rating,
            ratingBarStyle = RatingBarStyle.HighLighted,
            onValueChange = {
                rating = it
            }) {
            Log.d("TAG", "onRatingChanged: $it")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
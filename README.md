# Compose-Ratingbar
A ratingbar for jetpack compose 

[![](https://jitpack.io/v/a914-gowtham/compose-ratingbar.svg)](https://jitpack.io/#a914-gowtham/compose-ratingbar)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.a914-gowtham/compose-ratingbar.svg?label=mavenCentral)](https://search.maven.org/artifact/io.github.a914-gowtham/compose-ratingbar/1.1.0/aar)


<img src="https://github.com/a914-gowtham/compose-ratingbar/blob/main/demo_1.gif" width="340" height="260"/>

Download
--------
Add in project build.gradle:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

// App build.gradle
dependencies {
   implementation 'com.github.a914-gowtham:compose-ratingbar:1.1.1'
  //mavenCentral
  // implementation 'io.github.a914-gowtham:compose-ratingbar:1.1.1'

}
```

## Usage 
```kotlin
   import androidx.compose.runtime.*

   var rating: Float by remember { mutableStateOf(initialRating) }
    
   RatingBar(value = rating,
            ratingBarStyle = RatingBarStyle.HighLighted, onValueChange = {
            rating = it
        }) {
            Log.d("TAG", "onRatingChanged: $it")
   }
```

Ratingbar composable function has 13 params with default value as shown below
```kotlin
fun RatingBar(
    modifier: Modifier = Modifier,
    value: Float = 0f,
    numStars: Int = 5,
    size: Dp = 26.dp,
    padding: Dp = 2.dp,
    isIndicator: Boolean = false,
    activeColor: Color = Color(0xffffd740),
    inactiveColor: Color = Color(0xffffecb3),
    stepSize: StepSize = StepSize.ONE,
    hideInactiveStars: Boolean = false,
    ratingBarStyle: RatingBarStyle = RatingBarStyle.Normal,
    onValueChange: (Float) -> Unit,
    onRatingChanged: (Float) -> Unit
)
```

## Library Info
* Current version of the library only supports Stepsize 1f and 0.5f when click or drag.However, Initial Rating value could be any float value like 3.8 etc.

## Show some ❤ and support

Give a ⭐️ if this project helped you!

<a href="https://www.buymeacoffee.com/gowtham6672" target="_blank">
    <img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" width="160">
</a>

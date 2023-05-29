# Compose-Ratingbar
A ratingbar for jetpack compose 

[![](https://jitpack.io/v/a914-gowtham/compose-ratingbar.svg)](https://jitpack.io/#a914-gowtham/compose-ratingbar)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.a914-gowtham/compose-ratingbar.svg?label=mavenCentral)](https://search.maven.org/artifact/io.github.a914-gowtham/compose-ratingbar/1.1.0/aar)
[![](https://jitpack.io/v/a914-gowtham/compose-ratingbar/month.svg)](https://jitpack.io/#a914-gowtham/compose-ratingbar)


<img src="https://github.com/a914-gowtham/compose-ratingbar/blob/refactor/ratingbar_usage/demo_1.gif" width="340" height="260"/>

Download
--------
Add in project build.gradle:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

// App build.gradle
dependencies {
   implementation 'com.github.a914-gowtham:compose-ratingbar:1.3.4'
  //mavenCentral
  // implementation 'io.github.a914-gowtham:compose-ratingbar:1.2.3'

}
```

## Usage 
```kotlin
   import androidx.compose.runtime.*

   var rating: Float by remember { mutableStateOf(3.2f) }

   RatingBar(
       value = rating,
       style = RatingBarStyle.Fill(),
       onValueChange = {
           rating = it
       },
       onRatingChanged = {
           Log.d("TAG", "onRatingChanged: $it")
       }
   )
```

#### Other optional params:
```kotlin
  fun RatingBar(
      value: Float,
      modifier: Modifier = Modifier,
      numOfStars: Int = 5,
      size: Dp = 32.dp,
      spaceBetween: Dp = 6.dp,
      isIndicator: Boolean = false,
      stepSize: StepSize = StepSize.ONE,
      hideInactiveStars: Boolean = false,
      style: RatingBarStyle,
      onValueChange: (Float) -> Unit,
      onRatingChanged: (Float) -> Unit
)
```

## More Customization✨

<img src="https://github.com/a914-gowtham/compose-ratingbar/blob/main/customization_demo.gif" width="340" height="260"/>

Icon can be changed using ```painterEmpty``` ```painterFilled``` params.
```kotlin
        RatingBar(
            value = rating,
            painterEmpty = painterResource(id = R.drawable.icon_empty),
            painterFilled = painterResource(id = R.drawable.icon_filled),
            onValueChange = {
                rating = it
            },
            onRatingChanged = {
                Log.d("TAG", "onRatingChanged: $it")
            }
        )
```
## Library Info
* Forked from [compose-rating-bar](https://github.com/jsachica/compose-rating-bar)
* Current version of the library only supports Stepsize 1f and 0.5f when click or drag. However, Initial Rating value could be any float value like 3.8 etc.

## Show some ❤ and support

Give a ⭐️ if this project helped you!

<a href="https://www.buymeacoffee.com/gowtham6672" target="_blank">
    <img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" width="160">
</a>

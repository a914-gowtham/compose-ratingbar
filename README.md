# Compose-Ratingbar
A ratingbar for jetpack compose 

[![](https://jitpack.io/v/a914-gowtham/compose-ratingbar.svg)](https://jitpack.io/#a914-gowtham/compose-ratingbar)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.a914-gowtham/compose-ratingbar.svg?label=mavenCentral)](https://search.maven.org/artifact/io.github.a914-gowtham/compose-ratingbar/1.1.0/aar)
[![](https://jitpack.io/v/a914-gowtham/compose-ratingbar/month.svg)](https://jitpack.io/#a914-gowtham/compose-ratingbar)


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
   implementation 'com.github.a914-gowtham:compose-ratingbar:1.3.1'
  //mavenCentral
  // implementation 'io.github.a914-gowtham:compose-ratingbar:1.2.3'

}
```

## Usage 
```kotlin
   import androidx.compose.runtime.*

   var rating: Float by remember { mutableStateOf(initialRating) }

   RatingBar(
       value = rating,
       config = RatingBarConfig()
           .style(RatingBarStyle.HighLighted),
       onValueChange = {
           rating = it
       },
       onRatingChanged = {
           Log.d("TAG", "onRatingChanged: $it")
       }
   )
```

Ratingbar composable can be customized using  [RatingBarConfig](https://github.com/a914-gowtham/compose-ratingbar/blob/main/ratingbar/src/main/java/com/gowtham/ratingbar/RatingBarConfig.kt) class as shown below:
```kotlin
        RatingBarConfig()
                .activeColor(Color.Yellow)
                .hideInactiveStars(true)
                .inactiveColor(Color.LightGray)
                .inactiveBorderColor(Color.Blue)
                .stepSize(StepSize.HALF)
                .numStars(10)
                .isIndicator(true)
                .size(24.dp)
                .padding(6.dp)
                .style(RatingBarStyle.HighLighted)
```

## Customization

<img src="https://github.com/a914-gowtham/compose-ratingbar/blob/main/customization_demo.gif" width="340" height="260"/>

```kotlin
        RatingBar(
            value = rating,
            config = RatingBarConfig()
                .padding(2.dp)
                .size(32.dp),
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
* Current version of the library only supports Stepsize 1f and 0.5f when click or drag. However, Initial Rating value could be any float value like 3.8 etc.

## Show some ❤ and support

Give a ⭐️ if this project helped you!

<a href="https://www.buymeacoffee.com/gowtham6672" target="_blank">
    <img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" width="160">
</a>

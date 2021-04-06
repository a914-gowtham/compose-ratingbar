# Compose-Ratingbar
A ratingbar for jetpack compose 

[![](https://jitpack.io/v/a914-gowtham/compose-ratingbar.svg)](https://jitpack.io/#a914-gowtham/compose-ratingbar)

<img src="https://github.com/a914-gowtham/compose-ratingbar/blob/main/demo.gif" width="400" height="210"/>

Download
--------
Add in project build.gradle:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

// App build.gradle
dependencies {
   implementation 'com.github.a914-gowtham:compose-ratingbar:1.0.1-alpha02'
}
```

## Usage 
```kotlin
    var value: Int by rememberSaveable { mutableStateOf(3) }  //initial rating value is 3 here
  Column(){
       RatingBar(value = value,onRatingChanged = {
                value=it
                Log.d("TAG", "onRatingChanged: $it")
            })
        }
```

It has 10 params with default value as shown below
```kotlin
fun RatingBar(
    value: Int = 0,
    numStars: Int = 5, size: Dp = 34.dp, padding: Dp = 3.dp,
    isIndicator: Boolean = false, activeColor: Color = Color(0xffffd740),
    inactiveColor: Color = Color(0xffffecb3),
    stepSize: Float = 1f, onRatingChanged: (Int) -> Unit,
    @DrawableRes imgResId: Int=R.drawable.rounded_point_star
)
```
## Library Info
* Current version of this library does not support Stepsize except Stepsize 1f. It is under development


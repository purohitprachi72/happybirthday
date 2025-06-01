package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HappybirthdayTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HappybirthdayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingWithAnimatedBackground(
                        message = stringResource(R.string.happy_birthday),
                        from = stringResource(R.string.from_)
                    )
                }
            }
        }
    }
}

private fun lerp(start: Dp, stop: Dp, fraction: Float): Dp {
    return (start + (stop - start) * fraction)
}

@Composable
fun GreetingWithAnimatedBackground(message: String, from: String) {
    val pastelPink  = Color(0xFFF8BBD0)
    val lavender    = Color(0xFFE6E6FA)
    val mintGreen   = Color(0xFFC8E6C9)
    val pastelBlue  = Color(0xFFB3E5FC)
    val paleRose    = Color(0xFFFCE4EC)
    val paleSky     = Color(0xFFE1F5FE)
    val softPeach   = Color(0xFFFFE0B2)
    val softYellow  = Color(0xFFFFF9C4)

    val allBgColors = listOf(
        pastelPink, lavender,
        mintGreen, pastelBlue,
        paleRose, paleSky,
        softPeach, softYellow
    )

    var bgColorIndex by remember { mutableIntStateOf(0) }

    val backgroundColor by animateColorAsState(
        targetValue = allBgColors[bgColorIndex],
        animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing)
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            bgColorIndex = (bgColorIndex + 1) % allBgColors.size
        }
    }

    val textPulseTransition = rememberInfiniteTransition(label = "textPulse")
    val textScale by textPulseTransition.animateFloat(
        initialValue = 0.9f,
        targetValue  = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = androidx.compose.animation.core.EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "textScaleAnimation"
    )

    val fromFadeTransition = rememberInfiniteTransition(label = "fromFade")
    val fromAlpha by fromFadeTransition.animateFloat(
        initialValue = 0.5f,
        targetValue  = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = androidx.compose.animation.core.EaseOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fromAlphaAnimation"
    )

    data class FloatingEmoji(val emoji: String, val xOffsetDp: Dp, val delayMs: Int)

    val balloonConfigs = listOf(
//        FloatingEmoji("🎈", xOffsetDp = (-132).dp, delayMs =   527),
        FloatingEmoji("🎈", xOffsetDp =   45.dp,   delayMs =  1834),
//        FloatingEmoji("🎈", xOffsetDp =  249.dp,   delayMs =   112),
        FloatingEmoji("🎈", xOffsetDp =  (-87).dp, delayMs =  1540),
//        FloatingEmoji("🎈", xOffsetDp =  (-23).dp, delayMs =    39),
//        FloatingEmoji("🎈", xOffsetDp =  168.dp,   delayMs =   998),
//        FloatingEmoji("🎈", xOffsetDp =  (-45).dp, delayMs =   712),
//        FloatingEmoji("🎈", xOffsetDp =  201.dp,   delayMs =   366),
        FloatingEmoji("🎈", xOffsetDp =  (-10).dp, delayMs =  1265),
//        FloatingEmoji("🎈", xOffsetDp =   78.dp,   delayMs =   433)
    )

    val animatables = remember {
        balloonConfigs.map { Animatable(0f) }
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        while (true) {
            balloonConfigs.forEachIndexed { index, config ->
                scope.launch {
                    animatables[index].snapTo(0f)
                    delay(config.delayMs.toLong()) // staggered delay
                    animatables[index].animateTo(
                        targetValue = 1f,
                        animationSpec = TweenSpec(durationMillis = 10000, easing = Ease)
                    )
                }
            }
            delay(5000L)
        }
    }

    val fallingConfigs = listOf(
        FloatingEmoji("🎉", xOffsetDp = (-147).dp, delayMs = 1280),
        FloatingEmoji("🎊", xOffsetDp =   103.dp, delayMs =  320),
        FloatingEmoji("🥳️", xOffsetDp =  (-32).dp, delayMs = 1720),
        FloatingEmoji("🍰", xOffsetDp =   198.dp, delayMs =  470),
        FloatingEmoji("🍩", xOffsetDp = (-115).dp, delayMs =  930),
        FloatingEmoji("😇", xOffsetDp =   36.dp, delayMs = 1840),
        FloatingEmoji("👑", xOffsetDp = (-189).dp, delayMs =  150),
        FloatingEmoji("🧸", xOffsetDp =   57.dp, delayMs = 1130),
        FloatingEmoji("🍪", xOffsetDp =  122.dp, delayMs =  740),
        FloatingEmoji("🧁", xOffsetDp =  (-61).dp, delayMs = 2000),
        FloatingEmoji("🍫", xOffsetDp =  171.dp, delayMs =  290)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor), // Crossfades every 1s
        contentAlignment = Alignment.Center
    ) {
        balloonConfigs.forEachIndexed { index, config ->
            val fraction = animatables[index].value
            val startY = 600.dp
            val endY   = (-1200).dp
            val currentY = lerp(startY, endY, fraction)

            Text(
                text = config.emoji,
                fontSize = 60.sp,
                modifier = Modifier
                    .offset(x = config.xOffsetDp, y = currentY)
                    .alpha(0.8f)
            )
        }

        fallingConfigs.forEach { config ->
            val fallTransition = rememberInfiniteTransition(label = "fall${config.emoji}")
            val fallFraction by fallTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 5000
                        0f at config.delayMs
                        1f at 5000
                    },
                    repeatMode = RepeatMode.Restart
                ),
                label = "fallFraction${config.emoji}"
            )

            val fallY = lerp(start = (-500).dp, stop = 600.dp, fraction = fallFraction)

            Text(
                text = config.emoji,
                fontSize = 35.sp,
                modifier = Modifier
                    .offset(x = config.xOffsetDp, y = fallY)
                    .alpha(0.7f)
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                fontSize = 60.sp,
                lineHeight = 75.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .scale(textScale)
                    .padding(bottom = 8.dp)
            )

            Text(
                text = from,
                fontSize = 35.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(fromAlpha)
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HappyBirthdayPreview() {
    HappybirthdayTheme {
        GreetingWithAnimatedBackground(
            message = stringResource(R.string.happy_birthday),
            from = stringResource(R.string.from_)
        )
    }
}
package com.sporty.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sporty.challenge.ui.theme.SportyChallengeTheme
import com.sporty.weather.contract.WeatherContract

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            SportyChallengeTheme {
                /*
                 * Ideally we should have NavHost here, but for the sake of simplicity I decided to
                 * render screen this way.
                 */
                WeatherContract.Screen()
            }
        }
    }
}

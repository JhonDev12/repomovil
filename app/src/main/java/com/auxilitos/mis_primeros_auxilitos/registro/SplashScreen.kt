package com.auxilitos.mis_primeros_auxilitos.registro

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{ true }
        val i = Intent(this, Login::class.java)
        startActivity(i)
        finish()

        /*Versiones 12*/
        splashScreen.setOnExitAnimationListener { view ->
            view.iconView.let { icon ->
                // set an animator that goes from height to 0, it will use AnticipateInterpolator set at the bottom of this code
                val animator = ValueAnimator
                    .ofInt(icon.height, 0)
                    .setDuration(1000)
                //update the icon height and width every time the animator value change
                animator.addUpdateListener {
                    val value = it.animatedValue as Int
                    icon.layoutParams.width = value
                    icon.layoutParams.height = value
                    icon.requestLayout()
                    if (value == 0) {
                        view.remove()
                    }
                }
                val animationSet = AnimatorSet()
                animationSet.interpolator = AnticipateInterpolator()
                // Default tension of AnticipateInterpolator is 2,
                // this means that the animation will increase first the size of the icon a little bit and then decrease
                animationSet.play(animator)
                animationSet.start() // Launch the animation
            }
        }

    }
}
package com.acash.aris.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.asLiveData
import com.acash.aris.data.UserPreferencesDataStore

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        object : CountDownTimer(1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                UserPreferencesDataStore(this@SplashActivity).userName.asLiveData()
                    .observe(this@SplashActivity, {
                        if (it.isNullOrEmpty())
                            startActivity(
                                Intent(this@SplashActivity, IntroActivity::class.java).setFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                )
                            )
                        else startActivity(
                            Intent(this@SplashActivity, MainActivity::class.java).setFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            )
                        )
                    })
            }
        }.start()
    }
}
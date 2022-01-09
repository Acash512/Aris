package com.acash.aris.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acash.aris.adapters.ViewPagerAdapter
import com.acash.aris.databinding.ActivityIntroBinding
import com.acash.aris.fragments.IntroFragment1
import com.acash.aris.fragments.IntroFragment2
import com.acash.aris.fragments.IntroFragment3
import com.acash.aris.utils.FadeOutTransformation

class IntroActivity : AppCompatActivity() {

    private val fragments = arrayListOf(
        IntroFragment1(),
        IntroFragment2(),
        IntroFragment3()
    )

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager.adapter = ViewPagerAdapter(this, fragments)
        binding.viewpager.setPageTransformer(FadeOutTransformation())
    }
}
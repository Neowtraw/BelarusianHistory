package com.codingub.belarusianhistory.presentation.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.local.pref.ApplicationConfig
import com.codingub.belarusianhistory.databinding.ActivityMainBinding
import com.codingub.belarusianhistory.databinding.ActivitySplashBinding
import com.codingub.belarusianhistory.presentation.ui.MainActivity
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(ApplicationConfig.getTheme().nightMode)

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pushActivity(0)

        } else{
            setContentView(view)
            binding.tvMainText.typeface = Font.EXTRABOLD

            ImageUtil.load(AssetUtil.menuImageUri("icon")){
                binding.ivLogo.apply {
                    setImageDrawable(it)
                }
            }


            pushActivity(1500)
        }

    }

    fun pushActivity(time: Long){
        lifecycleScope.launch(Dispatchers.Main){
            delay(time)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)

        }
    }
}
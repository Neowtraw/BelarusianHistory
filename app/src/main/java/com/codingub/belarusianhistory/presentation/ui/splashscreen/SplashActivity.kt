package com.codingub.belarusianhistory.presentation.ui.splashscreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.ActivityMainBinding
import com.codingub.belarusianhistory.databinding.ActivitySplashBinding
import com.codingub.belarusianhistory.presentation.ui.MainActivity
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ImageUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            pushActivity()

        } else{
            setContentView(view)
            binding.tvMainText.typeface = Font.EXTRABOLD

            ImageUtil.load(AssetUtil.menuImageUri("icon")){
                binding.ivLogo.apply {
                    setImageDrawable(it)
                }
            }


            pushActivity()
        }

    }

    fun pushActivity(){
        lifecycleScope.launch(Dispatchers.Main){
            delay(1500)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
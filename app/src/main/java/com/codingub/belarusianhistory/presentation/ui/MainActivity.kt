package com.codingub.belarusianhistory.presentation.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.ActivityMainBinding
import com.codingub.belarusianhistory.presentation.ui.menu.MenuFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, MenuFragment())
                .commit()
        }

        setupToolbar()


    }

    //позже поменяю
    fun setupToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            val logo = ContextCompat.getDrawable(applicationContext, R.drawable.ic_launcher_icon)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(false)
            setLogo(logo)
            setDisplayUseLogoEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }




}
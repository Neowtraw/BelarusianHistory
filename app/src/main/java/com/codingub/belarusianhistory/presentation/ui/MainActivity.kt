package com.codingub.belarusianhistory.presentation.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.ActivityMainBinding
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.menu.MenuFragment
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val TIME_INTERVAL: Long = 2000 // Интервал времени между нажатиями в миллисекундах
    private var mBackPressedTime: Long = 0 // Время последнего нажатия

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var Instance: MainActivity? = null
        fun getInstance(): MainActivity = Instance!!

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Instance = this

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createToolbar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, MenuFragment())
                .commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu?.findItem(R.id.mSettings)?.let {
            it.iconTintList = ContextCompat.getColorStateList(this, R.color.icon_color_def)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId ==
            R.id.mSettings
        ) {
            // переход на сл фрагмент настроек
            true
        } else super.onOptionsItemSelected(item)
    }

    /*
        Creation
     */

    private fun createToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.apply {
            ImageUtil.load(AssetUtil.menuImageUri("icon")) {
                binding.ivTbLogo.setImageDrawable(it)
                binding.ivTbLogo.scaleType = ImageView.ScaleType.FIT_CENTER
            }
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    /*
        BackPress
     */
    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()

            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)

            if (fragment is BaseFragment && fragment !is MenuFragment) {
                val slideInDuration = 300L
                val slideInAnimation = TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
                )
                slideInAnimation.duration = slideInDuration

                fragment.view?.startAnimation(slideInAnimation)
                slideInAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationEnd(animation: Animation?) {
                        val transaction = fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .remove(fragment)
                            .commit()
                    }

                    override fun onAnimationRepeat(animation: Animation?) = Unit
                    override fun onAnimationStart(animation: Animation?) = Unit
                })
            }
        }else{
            val currentTime = System.currentTimeMillis()
            if (currentTime - mBackPressedTime > TIME_INTERVAL) {
                Toast.makeText(this, "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show()
                mBackPressedTime = currentTime
            } else {
                finishAffinity()
            }
        }
    }
}
package com.codingub.belarusianhistory.presentation.ui


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.local.pref.ApplicationConfig
import com.codingub.belarusianhistory.databinding.ActivityMainBinding
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.menu.MenuFragment
import com.codingub.belarusianhistory.presentation.ui.settings.SettingsFragment
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.ImageUtil
import com.codingub.belarusianhistory.utils.Resource
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
        super.onCreate(savedInstanceState)
        Instance = this

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
            pushFragment(SettingsFragment(), "settings")
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
        if (supportFragmentManager.backStackEntryCount > 0) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_left)
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
            currentFragment?.view?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left))
            supportFragmentManager.popBackStack()
            transaction.commit()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - mBackPressedTime > TIME_INTERVAL) {
                Toast.makeText(this, R.string.return_string, Toast.LENGTH_SHORT).show()
                mBackPressedTime = currentTime
            } else {
                finish()
            }
        }
    }

    fun pushFragment(fragment: BaseFragment, backstack: String?) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.apply {
            remove(supportFragmentManager.fragments.last())
            setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
            add(R.id.fragment_container_view, fragment)
        }

        if (backstack != null) {
            fragmentTransaction.addToBackStack(backstack)
        }

        fragmentTransaction.commit()
    }

//    override fun attachBaseContext(newBase: Context) {
//        super.attachBaseContext(newBase.setAppLocale(ApplicationConfig.getLanguage()))
//    }
}
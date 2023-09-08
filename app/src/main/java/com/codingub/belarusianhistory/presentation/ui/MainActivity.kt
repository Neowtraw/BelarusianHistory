package com.codingub.belarusianhistory.presentation.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.ActivityMainBinding
import com.codingub.belarusianhistory.presentation.ui.menu.MenuFragment
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
}
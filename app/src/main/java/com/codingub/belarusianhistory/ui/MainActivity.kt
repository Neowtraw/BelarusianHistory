package com.codingub.belarusianhistory.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.ActivityMainBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.TaskFragment
import com.codingub.belarusianhistory.ui.menu.MenuFragment
import com.codingub.belarusianhistory.ui.practice.PracticeInfoFragment
import com.codingub.belarusianhistory.ui.settings.SettingsFragment
import com.codingub.belarusianhistory.ui.tickets_info.TicketInfoFragment
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
            pushFragment(SettingsFragment(), "settings", R.id.fragment_container_view)
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

            if(supportFragmentManager.fragments.last() is TaskFragment ||
                supportFragmentManager.fragments.last() is PracticeInfoFragment ||
                supportFragmentManager.fragments.last() is TicketInfoFragment
            ){
                return
            }


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

    fun pushFragment(fragment: BaseFragment, backstack: String?, @IdRes fragmentView: Int) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.apply {
            remove(supportFragmentManager.fragments.last())
            setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
            add(fragmentView, fragment)
        }

        if (backstack != null) {
            fragmentTransaction.addToBackStack(backstack)
        }

        fragmentTransaction.commit()
    }

    fun pushChildFragment(parentFragment: BaseFragment, childFragment: TaskFragment,
         backstack: String?, @IdRes fragmentView: Int) {

        val fragmentTransaction: FragmentTransaction = parentFragment.childFragmentManager.beginTransaction()

        if ( parentFragment.childFragmentManager.fragments.isNotEmpty())
            fragmentTransaction.remove( parentFragment.childFragmentManager.fragments.last())

        fragmentTransaction.apply {
            setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
            add(fragmentView, childFragment)
        }

        if(backstack != null) fragmentTransaction.addToBackStack(backstack)
        fragmentTransaction.commit()
    }

}
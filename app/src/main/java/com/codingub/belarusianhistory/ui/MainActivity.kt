package com.codingub.belarusianhistory.ui


import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
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
import androidx.lifecycle.lifecycleScope
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.databinding.ActivityMainBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.TaskFragment
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialog
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialogView
import com.codingub.belarusianhistory.ui.menu.MenuFragment
import com.codingub.belarusianhistory.ui.settings.SettingsFragment
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.codingub.belarusianhistory.data.local.pref.ApplicationConfig
import com.codingub.belarusianhistory.ui.auth.login.LoginFragment
import com.codingub.belarusianhistory.ui.practice.PracticeInfoFragment
import com.codingub.belarusianhistory.ui.practice.result.ResultInfoFragment
import com.codingub.belarusianhistory.ui.tickets_info.TicketInfoFragment
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.setAppLocale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()

    private var isSettingsIconVisible = false
    private val TIME_INTERVAL: Long = 2000 // Интервал времени между нажатиями в миллисекундах
    private var mBackPressedTime: Long = 0 // Время последнего нажатия
    private var alertDialog: AlertDialog? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var Instance: MainActivity? = null
        fun getInstance(): MainActivity = Instance!!
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen().apply {
            setKeepOnScreenCondition {
                vm.isLoading.value
            }
        }
        AppCompatDelegate.setDefaultNightMode(ApplicationConfig.getTheme().nightMode)

        super.onCreate(savedInstanceState)
        Instance = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createToolbar()
        back()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, MenuFragment())
                .commit()
        }

    }

    /*
        Toolbar
     */

    private fun createToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.apply {
            ImageUtil.load(AssetUtil.menuImageUri("icon")) {
                binding.ivTbLogo.setImageDrawable(it)
                binding.ivTbLogo.scaleType = ImageView.ScaleType.FIT_CENTER
            }
            binding.ivTbLogo.setOnClickListener {
                if (supportFragmentManager.fragments.last() !is MenuFragment) {
                    pushFragment(MenuFragment(), "menu", R.id.fragment_container_view)
                }
            }
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    /*
        Navigation
     */

    private fun back() {
        onBackPressedDispatcher.addCallback(this) {

            if (supportFragmentManager.backStackEntryCount > 0 &&
                supportFragmentManager.fragments.last() !is MenuFragment
            ) {

                if (supportFragmentManager.fragments.last() is TaskFragment ||
                    supportFragmentManager.fragments.last() is PracticeInfoFragment ||
                    supportFragmentManager.fragments.last() is TicketInfoFragment
                ) {
                    showAlertDialog()
                    return@addCallback
                }
                if (supportFragmentManager.fragments.last() is ResultInfoFragment) {
                    pushFragment(MenuFragment(), "menu", R.id.fragment_container_view)
                    return@addCallback
                }

                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left)
                val currentFragment =
                    supportFragmentManager.findFragmentById(R.id.fragment_container_view)
                currentFragment?.view?.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@MainActivity,
                        R.anim.slide_in_left
                    )
                )
                supportFragmentManager.popBackStack()
                transaction.commit()
            } else {
                val currentTime = System.currentTimeMillis()
                if (currentTime - mBackPressedTime > TIME_INTERVAL) {
                    Toast.makeText(this@MainActivity, R.string.return_string, Toast.LENGTH_SHORT)
                        .show()
                    mBackPressedTime = currentTime
                } else {
                    finish()
                }
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

    fun pushChildFragment(
        parentFragment: BaseFragment, childFragment: TaskFragment,
        backstack: String?, @IdRes fragmentView: Int
    ) {

        val fragmentTransaction: FragmentTransaction =
            parentFragment.childFragmentManager.beginTransaction()

        if (parentFragment.childFragmentManager.fragments.isNotEmpty())
            fragmentTransaction.remove(parentFragment.childFragmentManager.fragments.last())

        fragmentTransaction.apply {
            setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
            add(fragmentView, childFragment)
        }

        if (backstack != null) fragmentTransaction.addToBackStack(backstack)
        fragmentTransaction.commit()
    }

    fun pushToMenu() {

        lifecycleScope.launch(Dispatchers.Main) {
            pushFragment(MenuFragment(), "menu", R.id.fragment_container_view)

        }

    }

    private fun showAlertDialog() {
        if (alertDialog != null) return

        val view = AlertDialogView.Builder(this)
            .message(R.string.back_task)
            .positiveButton(R.string.back_task_pos_button) {
                pushFragment(MenuFragment(), "menu", R.id.fragment_container_view)
                alertDialog?.dismiss()
            }
            .negativeButton(R.string.back_task_neg_button) {
                alertDialog?.dismiss()
            }
            .build()

        alertDialog = AlertDialog(this).apply {
            setView(view)
            setOnDismissListener {
                alertDialog = null
            }
        }.also { it.show() }
    }

    /*
        Menu
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isSettingsIconVisible) {
            menuInflater.inflate(R.menu.toolbar_menu, menu)
            menu?.findItem(R.id.mSettings)?.let {
                it.iconTintList = ContextCompat.getColorStateList(this, R.color.icon_color_def)
            }
            return true
        }
        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
        isSettingsIconVisible = currentFragment is MenuFragment
        invalidateOptionsMenu()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId ==
            R.id.mSettings
        ) {
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container_view)
            if (currentFragment is MenuFragment) pushFragment(
                SettingsFragment(),
                "settings",
                R.id.fragment_container_view
            )
            true
        } else super.onOptionsItemSelected(item)
    }

}
package com.codingub.belarusianhistory.ui


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.local.prefs.ApplicationConfig
import com.codingub.belarusianhistory.data.local.prefs.UserConfig
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.ActivityMainBinding
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.base.TaskFragment
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialog
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialogView
import com.codingub.belarusianhistory.ui.fragments.auth.LoginFragment
import com.codingub.belarusianhistory.ui.fragments.MenuFragment
import com.codingub.belarusianhistory.ui.fragments.practice.PracticeInfoFragment
import com.codingub.belarusianhistory.ui.fragments.auth.RegisterFragment
import com.codingub.belarusianhistory.ui.fragments.practice.ResultInfoFragment
import com.codingub.belarusianhistory.ui.fragments.auth.RoleFragment
import com.codingub.belarusianhistory.ui.fragments.auth.SettingsFragment
import com.codingub.belarusianhistory.ui.fragments.StatisticFragment
import com.codingub.belarusianhistory.ui.fragments.change.ChangeTicketFragment
import com.codingub.belarusianhistory.ui.fragments.map.MapFragment
import com.codingub.belarusianhistory.ui.fragments.map.MapTypeFragment
import com.codingub.belarusianhistory.ui.fragments.ticket.TicketInfoFragment
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()

    private var isSettingsIconVisible = false

    private val TIME_INTERVAL: Long = 2000L
    private var mBackPressedTime: Long = 0
    private var alertDialog: AlertDialog? = null


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var Instance: MainActivity? = null
        fun getInstance(): MainActivity = Instance!!
    }

    override fun attachBaseContext(newBase: Context) {
        val configuration = Configuration().apply {
            setLocale(Locale(ApplicationConfig.getLanguage().code))
        }
        val context = newBase.createConfigurationContext(configuration)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                vm.isLoading.value
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        Instance = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        hideSystemUI()

        createToolbar()
        back()

        observeChanges()
    }

    /*
        Toolbar
     */

    private fun createToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.apply {
            ImageUtil.load(AssetUtil.imagesImageUri("icon")) {
                binding.ivTbLogo.setImageDrawable(it)
                binding.ivTbLogo.scaleType = ImageView.ScaleType.FIT_CENTER
            }
            binding.ivTbLogo.setOnClickListener {
                when (supportFragmentManager.fragments.last()) {
                    is MenuFragment -> {
                        pushFragment(MapTypeFragment(), "statistic", R.id.fragment_container_view)
                    }

                    is RoleFragment, is RegisterFragment, is LoginFragment -> {}
                    else -> {
                        pushFragment(MenuFragment(), "menu", R.id.fragment_container_view)
                    }
                }

            }
            binding.tvLogin.apply {
                text = UserConfig.getLogin()
                typeface = Font.EXTRABOLD
            }
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun changeToolbarState() {
        when (supportFragmentManager.findFragmentById(R.id.fragment_container_view)) {
            is MenuFragment -> {
                Glide.with(this)
                    .load(AssetUtil.imagesImageUri("profile"))
                    .fitCenter()
                    .circleCrop()
                    .into(binding.ivTbLogo)
            }

            else -> {
                ImageUtil.load(AssetUtil.imagesImageUri("icon")) {
                    binding.ivTbLogo.apply {
                        setImageDrawable(it)
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }
                }
            }
        }
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
        supportActionBar?.show()
        if(currentFragment is LoginFragment || currentFragment is RegisterFragment || currentFragment is MapFragment){
            supportActionBar?.hide()
        }

        changeToolbarState()
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

    private fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    authState.collectLatest {
                        when (it) {
                            is ServerResponse.OK, is ServerResponse.Authorized -> {
                                supportFragmentManager.beginTransaction()
                                    .setReorderingAllowed(true)
                                    .add(R.id.fragment_container_view, MenuFragment())
                                    .commit()

                            }

                            is ServerResponse.Error, is ServerResponse.Unauthorized -> {
                                supportFragmentManager.beginTransaction()
                                    .setReorderingAllowed(true)
                                    .add(R.id.fragment_container_view, LoginFragment())
                                    .commit()
                            }

                            else -> {}
                        }
                    }
                }
            }
        }

    }

    /*
        Additional
     */

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

    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
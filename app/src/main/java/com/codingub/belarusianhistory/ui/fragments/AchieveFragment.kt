package com.codingub.belarusianhistory.ui.fragments

import android.graphics.Outline
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.custom.view.CategoryAchieveView
import com.codingub.belarusianhistory.sdk.AchievesCategory
import com.codingub.belarusianhistory.ui.adapters.AchieveAdapter
import com.codingub.belarusianhistory.ui.viewmodels.AchieveViewModel
import com.codingub.belarusianhistory.ui.base.BaseItemDecoration
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.setPaddingDp
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AchieveFragment : BaseFragment() {

    private val vm : AchieveViewModel by viewModels()

    private lateinit var mainText: TextView
    private lateinit var categoriesLayout: TabLayout
    private lateinit var rootLayout: MotionLayout
    private lateinit var achieveView: RecyclerView
    private lateinit var achieveAdapter: AchieveAdapter

    private lateinit var emptyText: TextView
    private lateinit var frameLayout: FrameLayout


    override fun create() {
        super.create()

        createMainText()
        createCategoriesLayout()
        createAchievesList()
        createFrameLayout()
        createRootLayout()

    }

    override fun viewCreated() {
        observeChanges()
    }

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {

        return rootLayout
    }

    private fun createMainText(){

        mainText = TextView(requireContext()).apply {
            id = View.generateViewId()
            text= Resource.string(R.string.achieves)
            setTextColor(Resource.color(R.color.text_color))
            textSize = 12f.dp
            typeface = Font.EXTRABOLD
        }
        emptyText = TextView(requireContext()).apply {
            id = View.generateViewId()
            text = Resource.string(R.string.empty_achieves)
            setTextColor(Resource.color(R.color.add_color))
            textSize = 12f.dp
            typeface = Font.REGULAR
            gravity = Gravity.CENTER
        }
    }

    private fun createCategoriesLayout(){
        categoriesLayout = TabLayout(requireContext()).apply {
            id = View.generateViewId()
            setBackgroundResource(R.color.bg)
            tabMode = TabLayout.MODE_AUTO
            overScrollMode = View.OVER_SCROLL_NEVER
            setSelectedTabIndicator(null)
            tabRippleColor = null
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRect(0, 5.dp, view.width, view.height)
                }
            }

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val categoryView = tab.customView as CategoryAchieveView
                    categoryView.setChecked(true, animated = true)
                    lifecycleScope.launch(Dispatchers.IO){
                        vm.updateAchieves(categoryView.category)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    (tab.customView as CategoryAchieveView).setChecked(false, animated = true)
                }

                override fun onTabReselected(tab: TabLayout.Tab) = Unit
            })

        }

        val categories = AchievesCategory.values()
        categories.forEachIndexed { index, category ->
            categoriesLayout.apply {
                newTab().apply {
                    customView = CategoryAchieveView(requireContext(), category)
                    view.setPaddingDp(
                        if (index == 0) 16 else 4,
                        0,
                        if (index == categories.lastIndex) 16 else 4,
                        0
                    )
                }.also {
                    addTab(it)
                }
            }
        }
    }

    private fun createAchievesList(){

        achieveView = RecyclerView(requireContext()).apply {
            id = View.generateViewId()
            setBackgroundResource(R.color.bg)
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(requireContext())
            achieveAdapter = AchieveAdapter()
            adapter = achieveAdapter
            addItemDecoration(BaseItemDecoration())
        }
    }

    private fun createFrameLayout(){
        frameLayout = FrameLayout(requireContext()).apply {
            id = View.generateViewId()

            addView(achieveView, ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ))
            addView(emptyText, ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                600.dp
            ))
        }

    }

    private fun createRootLayout(){
        rootLayout = MotionLayout(requireContext()).apply {
            setBackgroundResource(R.color.bg)
            setPadding(Resource.dimen(R.dimen.main_padding).toInt())

            addView(mainText, ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0,20.dp,0)
            })

            addView(categoriesLayout, ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT
            ))

            addView(
                frameLayout, ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
            )

        }

        val startSet = ConstraintSet().apply{
            clone(rootLayout)

            connect(mainText.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
            setAlpha(mainText.id, 1f)

            connect(categoriesLayout.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
            connect(categoriesLayout.id, ConstraintSet.TOP, mainText.id, ConstraintSet.BOTTOM, 20.dp)
            connect(categoriesLayout.id, ConstraintSet.END,  ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
            setAlpha(categoriesLayout.id, 1f)
            connect(frameLayout.id, ConstraintSet.TOP, categoriesLayout.id, ConstraintSet.BOTTOM, 16.dp)


            applyTo(rootLayout)
        }

        val endSet = ConstraintSet().apply{
            clone(rootLayout)

            connect(mainText.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
            setAlpha(mainText.id, 0f)

            connect(categoriesLayout.id, ConstraintSet.TOP, mainText.id, ConstraintSet.BOTTOM, 0)
            connect(categoriesLayout.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
            connect(categoriesLayout.id, ConstraintSet.END,  ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
            setAlpha(categoriesLayout.id, 0f)
            connect(frameLayout.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16.dp)

        }

        val motionScene = MotionScene(rootLayout)

        val transition = TransitionBuilder.buildTransition(
            motionScene,
            View.generateViewId(),
            View.generateViewId(),
            startSet,
            View.generateViewId(),
            endSet
        ).apply {
            duration = 100
            setOnSwipe(
                OnSwipe().apply {
                    setMaxAcceleration(50)
                    onTouchUp = OnSwipe.ON_UP_AUTOCOMPLETE
                    dragDirection = OnSwipe.DRAG_UP
                    touchAnchorId = frameLayout.id
                }
            )
        }.also {
            motionScene.addTransition(it)
        }

        rootLayout.apply {
            scene = motionScene
            setTransition(transition.id)
        }


    }


    override fun observeChanges() {
        vm.displayedAchieves.observe(this@AchieveFragment){

            if(it.isNullOrEmpty()){
                achieveAdapter.achieves = emptyList()
                emptyText.visibility = View.VISIBLE
                achieveView.visibility = View.INVISIBLE
            }else{
                achieveAdapter.achieves = it
                emptyText.visibility = View.INVISIBLE
                achieveView.visibility = View.VISIBLE
            }

            Log.d("",it.size.toString())

        }
    }

}
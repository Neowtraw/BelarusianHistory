package com.codingub.belarusianhistory.ui.custom.view.statistic

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.remote.network.models.userdata.Group
import com.codingub.belarusianhistory.ui.base.BaseItemDecoration
import com.codingub.belarusianhistory.utils.AssetUtil
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.dp
import com.codingub.belarusianhistory.utils.extension.textSizeDp

/**
 * [StatisticGroupView] shows user's groups in the [StatisticFragment]
 */
@SuppressLint("ViewConstructor")
class StatisticGroupView(
    groups: List<Group>,
    context: Context
) : LinearLayoutCompat(context) {

    private val groupsView: TextView
    private val add: ImageView
    private val reset: ImageView

    private val groupLayout: RelativeLayout

    private val rvGroups: RecyclerView
    private val groupAdapter: GroupAdapter

    private val gradient: GradientDrawable

    init {
        setWillNotDraw(false)
        orientation = VERTICAL
        setPadding(Resource.dimen(R.dimen.main_padding).toInt())

        groupLayout = RelativeLayout(context)

        groupsView = TextView(context).apply {
            id = generateViewId()
            typeface = Font.SEMIBOLD
            textSizeDp = 8f.dp
            isSingleLine = true
            text = Resource.string(R.string.groups)
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Resource.color(R.color.text_color))
            gravity = Gravity.CENTER
        }
        groupLayout.addView(groupsView, RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
            addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
        })

        //reset
        reset = ImageView(context).apply {
            id = generateViewId()
            post {
                Glide.with(this)
                    .load(AssetUtil.imagesImageUri("minus"))
                    .fitCenter()
                    .into(this)
                setColorFilter(Resource.color(R.color.contrast))
            }
        }
        groupLayout.addView(reset, RelativeLayout.LayoutParams(
            20.dp,
            20.dp
        ).apply {
            addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
            addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
        })

        //add
        add = ImageView(context).apply {
            post {
                Glide.with(this)
                    .load(AssetUtil.imagesImageUri("plus"))
                    .fitCenter()
                    .into(this)
                setColorFilter(Resource.color(R.color.contrast))
            }
        }
        groupLayout.addView(add, RelativeLayout.LayoutParams(
            20.dp,
            20.dp
        ).apply {
            addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
            addRule(RelativeLayout.LEFT_OF, reset.id)
            setMargins(0,0,10.dp,0)
        })



        rvGroups = RecyclerView(context).apply {
            groupAdapter = GroupAdapter(groups)
            adapter = groupAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(BaseItemDecoration())
        }

        addView(
            groupLayout, LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply{
            setMargins(0,0,0,16.dp)}
        )

        addView(rvGroups, LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        ))


        //background
        gradient = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            color = ColorStateList.valueOf(Resource.color(R.color.bg_btn))
            cornerRadius = resources.getDimension(R.dimen.corner_icon_radius)

        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        gradient.setBounds(0, 0, width, height)
        gradient.draw(canvas)
    }

}
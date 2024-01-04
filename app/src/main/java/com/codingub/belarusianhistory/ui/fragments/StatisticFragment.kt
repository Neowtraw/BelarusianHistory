package com.codingub.belarusianhistory.ui.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingub.belarusianhistory.R
import com.codingub.belarusianhistory.data.local.prefs.UserConfig
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.databinding.FragmentStatisticBinding
import com.codingub.belarusianhistory.sdk.AccessLevel
import com.codingub.belarusianhistory.ui.adapters.group.GroupAdapter
import com.codingub.belarusianhistory.ui.adapters.group.GroupStatisticAdapter
import com.codingub.belarusianhistory.ui.base.BaseFragment
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialog
import com.codingub.belarusianhistory.ui.custom.dialog.AlertDialogInputView
import com.codingub.belarusianhistory.ui.custom.view.statistic.StatisticUserView
import com.codingub.belarusianhistory.ui.viewmodels.StatisticViewModel
import com.codingub.belarusianhistory.utils.Font
import com.codingub.belarusianhistory.utils.ItemDecoration
import com.codingub.belarusianhistory.utils.Resource
import com.codingub.belarusianhistory.utils.extension.applyScaleTouch
import com.codingub.belarusianhistory.utils.extension.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatisticFragment : BaseFragment() {

    private val vm: StatisticViewModel by viewModels()
    private lateinit var binding: FragmentStatisticBinding

    private lateinit var groupsAdapter: GroupAdapter
    private lateinit var groupStatisticAdapter: GroupStatisticAdapter
    private lateinit var statisticUserView: StatisticUserView

    private var alertDialog: AlertDialog? = null

    override fun createView(inf: LayoutInflater, con: ViewGroup?, state: Bundle?): View {
        binding = FragmentStatisticBinding.inflate(inf, con, false)

        createUI()
        createUserView()
        createGroupView()
        createGroupStatisticView()

        setupListeners()
        observeChanges()
        return binding.root
    }

    private fun createUI() {
        binding.tvStatistic.typeface = Font.EXTRABOLD
        binding.groupStatistic.tvName.typeface = Font.EXTRABOLD
        binding.groupStatistic.tvName.text = Resource.string(R.string.groups)
        binding.membersStatistic.tvName.typeface = Font.EXTRABOLD
        binding.btnDeleteGroup.typeface = Font.REGULAR
        binding.btnDeleteGroup.applyScaleTouch()
        binding.btnGroupsRepeat.typeface = Font.REGULAR
        binding.groupsEmpty.typeface = Font.REGULAR
        binding.tvMembersEmpty.typeface = Font.REGULAR

        if (UserConfig.getAccessLevel() == AccessLevel.User) {
            binding.groupsEmpty.text = Resource.string(R.string.user_groups_empty)
            binding.groupStatistic.btnAdd.visibility = View.GONE
        }
        binding.groupsEmpty.text = Resource.string(R.string.teacher_groups_empty)
        binding.groupStatistic.btnAdd.visibility = View.VISIBLE
    }

    private fun createUserView() {

        //TEST!!!
        statisticUserView = StatisticUserView(
            context = context,
            username = UserConfig.getUsername(),
            passedTickets = 1,
            allTickets = 2,
            passedPractice = 1,
            allPractice = 3,
            passedAchieves = 2,
            allAchieves = 5
        )
        binding.llStatistic.addView(
            statisticUserView, ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
        )

        val layoutParams = statisticUserView.layoutParams as LinearLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, 20.dp)
    }

    private fun createGroupView() {
        binding.rvGroups.apply {
            groupsAdapter = GroupAdapter {
                val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.dialog_in)
                binding.flMembers.startAnimation(anim)
                binding.flMembers.visibility = View.VISIBLE
                binding.membersStatistic.tvName.text = it.name
                vm.curGroup.value = it
            }
            adapter = groupsAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(ItemDecoration.createLinBottomItemDecoration(9.dp))
        }
    }

    private fun createGroupStatisticView() {
        val shadowColorValue = Resource.color(R.color.black)
        val shapeDrawable = ShapeDrawable()
        shapeDrawable.setTint(shadowColorValue)
        val shadowBlur = binding.Members.paddingBottom - 12.dp
        shapeDrawable.paint.setShadowLayer(
            shadowBlur.toFloat(), //blur
            0f, //dx
            0f, //dy
            Resource.color(R.color.contrast) //color
        )
        val radius = 27f.dp
        val outerRadius = floatArrayOf(
            radius, radius, //top-left
            radius, radius, //top-right
            radius, radius, //bottom-right
            radius, radius  //bottom-left
        )
        shapeDrawable.shape = RoundRectShape(outerRadius, null, null)
        val drawable = LayerDrawable(arrayOf<Drawable>(shapeDrawable))
        val inset = binding.Members.paddingBottom
        drawable.setLayerInset(
            0,
            inset,
            inset,
            inset,
            inset
        )
        binding.Members.background = drawable
        binding.rvMembers.apply {
            visibility = View.VISIBLE
            groupStatisticAdapter = GroupStatisticAdapter() {
                vm.deleteUserFromGroup(it, vm.curGroup.value!!.id)
            }
            adapter = groupStatisticAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(ItemDecoration.createLinBottomItemDecoration(9.dp))
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        binding.groupsError.setOnClickListener {
            vm.getGroups()
        }
        binding.groupStatistic.btnAdd.setOnClickListener {
            showAlertDialog(
                R.string.add_new_group, R.string.group_name_hint, R.string.add_action,
                R.string.cancel_action
            ) {
                if (it.isEmpty()) {
                    Toast.makeText(requireContext(), "Cannot be empty", Toast.LENGTH_SHORT).show()
                    return@showAlertDialog
                }
                vm.createGroup(it)
            }
        }

        binding.flMembers.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.rawX.toInt() - binding.flMembers.left
                val y = event.rawY.toInt() - binding.flMembers.top

                if (!isPointInsideView(x, y, binding.Members)) {
                    val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.dialog_out)
                    binding.flMembers.startAnimation(anim)
                    binding.flMembers.visibility = View.GONE
                    vm.curGroup.value = null
                }
            }
            true
        }

        binding.btnDeleteGroup.setOnClickListener {
            vm.deleteGroup(vm.curGroup.value!!.teacher, vm.curGroup.value!!.id)
        }
        binding.membersStatistic.btnAdd.setOnClickListener {
            showAlertDialog(
                R.string.add_new_user,
                R.string.login_user_hint,
                R.string.add_action,
                R.string.cancel_action
            ) {
                if (it.isEmpty()) {
                    Toast.makeText(requireContext(), "Cannot be empty", Toast.LENGTH_SHORT).show()
                    return@showAlertDialog
                }
                vm.inviteUserToGroup(it, vm.curGroup.value!!.id)
            }
        }
    }

    override fun observeChanges() {
        with(vm) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    createGroupState.collectLatest {
                        when (it) {
                            is ServerResponse.Loading -> {
                                /**я
                                 * Здесь должен быть прогресс бар на весь экран в квадратике
                                 */

                            }

                            is ServerResponse.OK -> {
                                vm.getGroups()
                                alertDialog?.dismiss()
                            }

                            is ServerResponse.Error -> {
                                Toast.makeText(
                                    requireContext(),
                                    it.errorMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                                alertDialog?.dismiss()
                            }

                            else -> {}
                        }
                    }
                }
            }
            lifecycleScope.launch {
                groupState.collectLatest {
                    when (it) {
                        is ServerResponse.Loading -> {
                            binding.rvGroups.visibility = View.GONE
                            binding.groupsEmpty.visibility = View.GONE
                            binding.groupsError.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ServerResponse.OK -> {
                            binding.progressBar.visibility = View.GONE

                            groupsAdapter.groups = it.value!!

                            curGroup.value?.let { group ->
                                val matchingGroup =
                                    it.value.find { lgroup -> lgroup.id == group.id }
                                if (matchingGroup != null) vm.curGroup.value = matchingGroup
                            }
                            groupsAdapter.notifyItemRangeChanged(0, groupsAdapter.itemCount)

                            if (it.value.isEmpty()) {
                                binding.groupsEmpty.visibility = View.VISIBLE
                                return@collectLatest
                            }

                            binding.rvGroups.visibility = View.VISIBLE
                        }

                        is ServerResponse.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                it.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.groupsError.visibility = View.VISIBLE
                        }

                        else -> {}
                    }
                }
            }
            curGroup.observe(viewLifecycleOwner){
                if (it != null) {
                    binding.tvMembersEmpty.visibility = View.GONE

                    groupStatisticAdapter.users = it.users
                    groupStatisticAdapter.notifyItemRangeChanged(0, groupStatisticAdapter.itemCount)

                    if (it.users.isEmpty()){
                        binding.tvMembersEmpty.visibility = View.VISIBLE
                        return@observe
                    }
                }
            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    deleteGroupState.collectLatest {
                        when (it) {
                            is ServerResponse.Loading -> {
                                /**
                                 * Здесь должен быть прогресс бар на весь экран в квадратике
                                 */
                            }
                            is ServerResponse.OK -> {
                                vm.getGroups()
                                binding.flMembers.visibility = View.GONE
                            }
                            is ServerResponse.Error -> {
                                Toast.makeText(
                                    requireContext(),
                                    it.errorMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {}
                        }
                    }
                }
            }
            lifecycleScope.launch {
                inviteUserGroupState.collectLatest {
                    when (it) {
                        is ServerResponse.Loading -> {

                        }
                        is ServerResponse.OK -> {
                            vm.getGroups()
                            alertDialog?.dismiss()
                        }
                        is ServerResponse.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                it.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.groupsError.visibility = View.VISIBLE
                        }
                        else -> {}
                    }
                }
            }
            lifecycleScope.launch {
                deleteUserGroupState.collectLatest {
                    when (it) {
                        is ServerResponse.Loading -> {

                        }

                        is ServerResponse.OK -> {
                            vm.getGroups()
                            if (groupStatisticAdapter.itemCount == 0)
                                binding.tvMembersEmpty.visibility = View.VISIBLE
                            alertDialog?.dismiss()
                        }

                        is ServerResponse.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                it.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.groupsError.visibility = View.VISIBLE
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    /*
        Additional
     */

    private fun showAlertDialog(
        title: Int, hint: Int, posButton: Int, negButton: Int,
        onPositiveButtonPressed: (String) -> Unit
    ) {
        if (alertDialog != null) return

        val view = AlertDialogInputView.Builder(requireContext())
            .title(title)
            .hint(hint)
            .positiveButton(posButton) {
                onPositiveButtonPressed(it)
            }
            .negativeButton(negButton) {
                alertDialog?.dismiss()
            }
            .build()

        alertDialog = AlertDialog(requireContext()).apply {
            setView(view)
            setOnDismissListener {
                alertDialog = null
            }
        }.also { it.show() }
    }

    private fun isPointInsideView(x: Int, y: Int, view: View): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)

        val left = location[0]
        val top = location[1]
        val right = left + view.width
        val bottom = top + view.height

        return x in left..right && y in top..bottom
    }
}
package com.codingub.belarusianhistory.sdk

import com.codingub.belarusianhistory.presentation.ui.achieves.AchieveFragment
import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.menu.MenuFragment
import com.codingub.belarusianhistory.presentation.ui.tickets_practice.practice.PracticeFragment
import com.codingub.belarusianhistory.presentation.ui.tickets_practice.tickets.TicketsFragment

//для перехода на иной фрагмент
enum class FragmentType(val fragment: BaseFragment) {

    ACHIEVES(AchieveFragment()),
    EVENTS(MenuFragment()),
    TICKETS(TicketsFragment()),
    PRACTICE(PracticeFragment())

}

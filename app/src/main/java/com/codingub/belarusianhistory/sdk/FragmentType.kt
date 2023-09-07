package com.codingub.belarusianhistory.sdk

import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.menu.MenuFragment
import com.codingub.belarusianhistory.presentation.ui.tickets.TicketsFragment

//для перехода на иной фрагмент
sealed class FragmentType {

    data object ACHIEVES : FragmentType() {
        fun getFragment(): BaseFragment {
            return MenuFragment()
        }
    }

    data object EVENTS : FragmentType() {
        fun getFragment(): BaseFragment {
            return MenuFragment()
        }
    }

    data object TICKETS : FragmentType(){
        fun getFragment(): BaseFragment {
            return TicketsFragment()
        }
    }

    data object PRACTICE : FragmentType(){
        fun getFragment(): BaseFragment {
            return MenuFragment()
        }
    }
}

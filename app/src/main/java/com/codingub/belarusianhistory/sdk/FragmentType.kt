package com.codingub.belarusianhistory.sdk

import com.codingub.belarusianhistory.presentation.ui.base.BaseFragment
import com.codingub.belarusianhistory.presentation.ui.menu.MenuFragment
import com.codingub.belarusianhistory.presentation.ui.tickets_practice.TicketsFragment

//для перехода на иной фрагмент
sealed class FragmentType {

    object ACHIEVES : FragmentType() {
        fun getFragment(): BaseFragment {
            return MenuFragment()
        }
    }

    object EVENTS : FragmentType() {
        fun getFragment(): BaseFragment {
            return MenuFragment()
        }
    }

    object TICKETS : FragmentType(){
        fun getFragment(): BaseFragment {
            return TicketsFragment()
        }
    }

    object PRACTICE : FragmentType(){
        fun getFragment(): BaseFragment {
            return MenuFragment()
        }
    }
}

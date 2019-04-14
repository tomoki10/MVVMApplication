package com.dev.megaloma.mvvmapplication.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabsPagerAdapter(fm: FragmentManager,
                       private val tabsFragments: ArrayList<Class<out Fragment>>,
                       setTabName: ArrayList<String>) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf<CharSequence>(setTabName[0], setTabName[1])

    override fun getItem(position: Int): Fragment {
        return tabsFragments[position].newInstance()
    }

    override fun getCount(): Int {
        return tabsFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}
package com.student.diary.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *  Adapter class for managing fragments in a ViewPager*/
class ContactDetailsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    /** List to hold the fragments*/
    private val mFragments: MutableList<Fragment> = ArrayList<Fragment>()

    /** List to hold the titles of the fragments*/
    private val mStrings: MutableList<String> = ArrayList()

    /**
     *
     * Returns the fragment to display for a given position
     *
     */
    override fun getItem(position: Int): Fragment {
        return mFragments[position] // Return the fragment at the specified position
    }

    /**
     *
     *
     * Returns the total number of fragments
     *
     */
    override fun getCount(): Int {
        return mStrings.size // Return the number of fragment titles, which corresponds to the number of fragments
    }

    /**
     * Returns the title of the page for a given position
     * */
    override fun getPageTitle(position: Int): CharSequence {
        return mStrings[position] // Return the title at the specified position
    }

    /**
     * Adds a fragment and its title to the adapter
     * */
    fun addFragment(fragment: Fragment, title: String) {
        mFragments.add(fragment) // Add the fragment to the list of fragments
        mStrings.add(title) // Add the title to the list of titles
    }
}
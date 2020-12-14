package com.example.inventarywithfirebase

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ProductPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return arrayOf(EditUser(), ProductAll(),ProductNewEdit())[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return arrayOf("My Account","All","New product")[position]
    }
}
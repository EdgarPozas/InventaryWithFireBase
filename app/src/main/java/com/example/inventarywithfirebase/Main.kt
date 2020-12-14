package com.example.inventarywithfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirestoreRegistrar

class Main : AppCompatActivity() {

    var user:DataUser?=null
    var tabLayout:TabLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        user=intent.getSerializableExtra("user") as? DataUser

        title="${user?.Name} ${user?.LastName}"

        val adapter=ProductPageAdapter(supportFragmentManager)
        val viewPager=findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter=adapter

        tabLayout=findViewById<TabLayout>(R.id.tabs)
        tabLayout?.setupWithViewPager(viewPager)

        setTab(1)
    }

    fun setTab(tab:Int){
        tabLayout?.selectTab(tabLayout?.getTabAt(tab))
    }
}
package com.example.mvvmdemomaster.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.youngbrains.testandroid.MainActivity


fun FragmentActivity.addReplaceFragment(
        fragment: Fragment,
        addFragment: Boolean,
        backAllow: Boolean,
        frameLayout: Int
    ) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if (addFragment) {
            transaction.add(frameLayout,fragment)
        }else{
            transaction.replace(frameLayout,fragment)
        }
        if (backAllow) {
            transaction.addToBackStack(MainActivity().toString())
        } else {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }



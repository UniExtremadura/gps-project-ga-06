package com.aseegpsproject.openbook.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class RecyclerViewItemCountAction : ViewAction {
    private var itemCount = 0

    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isAssignableFrom(RecyclerView::class.java)
    }

    override fun getDescription(): String {
        return "Get the number of items in a RecyclerView"
    }

    override fun perform(uiController: UiController, view: View) {
        val recyclerView = view as RecyclerView
        itemCount = recyclerView.adapter?.itemCount ?: 0
    }

    fun getItemCount(): Int {
        return itemCount
    }
}
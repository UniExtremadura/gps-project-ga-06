package com.aseegpsproject.openbook.view


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.aseegpsproject.openbook.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class GetWorksListWorkTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun getWorksListWorkTest() {
        val materialButton = onView(withId(R.id.btnRegister))
        materialButton.perform(click())

        val appCompatEditText = onView(withId(R.id.etUsername))
        appCompatEditText.perform(replaceText("espresso"), closeSoftKeyboard())

        val appCompatEditText2 = onView(withId(R.id.etPassword))
        appCompatEditText2.perform(replaceText("latte"), closeSoftKeyboard())

        val appCompatEditText3 = onView(withId(R.id.etRepeatPassword))
        appCompatEditText3.perform(replaceText("latte"), closeSoftKeyboard())

        val materialButton2 = onView(withId(R.id.btnRegister))
        materialButton2.perform(click())

        val bottomNavigationItemView = onView(withId(R.id.profileFragment))
        bottomNavigationItemView.perform(click())

        val materialButton3 = onView(withId(R.id.btn_add_worklist))
        materialButton3.perform(click())

        val appCompatEditText4 = onView(withId(R.id.et_worklist_name))
        appCompatEditText4.perform(replaceText("dummyGetWorksListWorks"), closeSoftKeyboard())

        val materialButton4 = onView(withId(R.id.btn_create_worklist))
        materialButton4.perform(click())

        val bottomNavigationItemView2 = onView(withId(R.id.discoverFragment))
        bottomNavigationItemView2.perform(click())

        val recyclerView = onView(withId(R.id.rv_book_list))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        val button = onView(withId(R.id.btn_add_to_worklist))
        button.check(matches(isDisplayed()))
        button.perform(scrollTo(), click())

        val recyclerView2 = onView(withId(R.id.rv_worklist_list))
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val bottomNavigationItemView3 = onView(withId(R.id.profileFragment))
        bottomNavigationItemView3.perform(click())

        val recyclerView3 = onView(withId(R.id.rv_worklist_list))
        recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView = onView(withId(R.id.work_title))
        textView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

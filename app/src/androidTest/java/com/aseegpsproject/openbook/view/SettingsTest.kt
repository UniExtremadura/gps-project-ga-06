package com.aseegpsproject.openbook.view


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onData
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
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SettingsTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun settingsTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.btnRegister), withText("Register"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.etUsername),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("espresso"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.etPassword),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("latte"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.etRepeatPassword),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("latte"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btnRegister), withText("Register"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val textView = onView(
            allOf(
                withId(androidx.appcompat.R.id.title), withText("Settings"),
                withParent(withParent(withId(androidx.appcompat.R.id.content))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Settings")))

        val materialTextView = onView(
            allOf(
                withId(androidx.appcompat.R.id.title), withText("Settings"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textView2 = onView(
            allOf(
                withText("Settings"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Settings")))

        val recyclerView = onView(
            allOf(
                withId(androidx.preference.R.id.recycler_view),
                childAtPosition(
                    withId(android.R.id.list_container),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        val recyclerView2 = onView(
            allOf(
                withId(androidx.preference.R.id.recycler_view),
                childAtPosition(
                    withId(android.R.id.list_container),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(3, click()))

        Thread.sleep(1000)

        val button = onView(
            allOf(
                withId(android.R.id.button2), withText("Cancel"),
                withParent(withParent(withId(androidx.appcompat.R.id.buttonPanel))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val appCompatCheckedTextView = onData(anything())
            .inAdapterView(
                allOf(
                    withId(androidx.appcompat.R.id.select_dialog_listview),
                    childAtPosition(
                        withId(androidx.appcompat.R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(0)
        appCompatCheckedTextView.perform(click())

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

        val textView3 = onView(
            allOf(
                withId(R.id.work_title), withText("To Kill a Mockingbird"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("To Kill a Mockingbird")))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.profileFragment), withContentDescription("Profile"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btn_settings),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val textView4 = onView(
            allOf(
                withText("Settings"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Settings")))
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

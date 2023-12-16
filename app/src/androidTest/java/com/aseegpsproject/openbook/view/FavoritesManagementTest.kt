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
class FavoritesManagementTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun favoritesManagementTest() {
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

        val recyclerView = onView(
            allOf(
                withId(R.id.rv_book_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, longClick()))

        val recyclerView2 = onView(
            allOf(
                withId(R.id.rv_book_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, longClick()))

        val recyclerView3 = onView(
            allOf(
                withId(R.id.rv_book_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(1, longClick()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.worksFragment), withContentDescription("Books"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.work_title), withText("The Catcher in the Rye"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("The Catcher in the Rye")))

        val textView2 = onView(
            allOf(
                withId(R.id.work_title), withText("The Adventures of Huckleberry Finn"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("The Adventures of Huckleberry Finn")))

        val textView3 = onView(
            allOf(
                withId(R.id.work_title), withText("To Kill a Mockingbird"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("To Kill a Mockingbird")))

        val textView4 = onView(
            allOf(
                withId(R.id.work_title), withText("To Kill a Mockingbird"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("To Kill a Mockingbird")))

        val recyclerView4 = onView(
            allOf(
                withId(R.id.rv_books_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView4.perform(actionOnItemAtPosition<ViewHolder>(1, longClick()))

        val textView5 = onView(
            allOf(
                withId(R.id.work_title), withText("The Catcher in the Rye"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("The Catcher in the Rye")))

        val textView6 = onView(
            allOf(
                withId(R.id.work_title), withText("To Kill a Mockingbird"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("To Kill a Mockingbird")))

        val recyclerView5 = onView(
            allOf(
                withId(R.id.rv_books_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView5.perform(actionOnItemAtPosition<ViewHolder>(1, longClick()))

        val viewGroup = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.discover_cv_Item),
                        withParent(withId(R.id.discover_cl_item))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val recyclerView6 = onView(
            allOf(
                withId(R.id.rv_books_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView6.perform(actionOnItemAtPosition<ViewHolder>(0, longClick()))

        val viewGroup1 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.discover_cv_Item),
                        withParent(withId(R.id.discover_cl_item))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup1.check(doesNotExist())
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

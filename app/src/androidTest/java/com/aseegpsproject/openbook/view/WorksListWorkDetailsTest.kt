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
class WorksListWorkDetailsTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun worksListWorkDetailsTest() {
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
                withId(R.id.btn_add_worklist),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.et_worklist_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cv_create_worklist),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("lista de ejemplo"), closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(R.id.btn_create_worklist), withText("Add"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cv_create_worklist),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.discoverFragment), withContentDescription("Discover"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.rv_book_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        val materialButton5 = onView(
            allOf(
                withId(R.id.btn_add_to_worklist), withContentDescription("Add"),
                childAtPosition(
                    allOf(
                        withId(R.id.headConstraintLayout),
                        childAtPosition(
                            withId(R.id.work_details),
                            0
                        )
                    ),
                    0
                )
            )
        )
        materialButton5.perform(scrollTo(), click())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.rv_worklist_list),
                childAtPosition(
                    withId(R.id.constraintLayout),
                    2
                )
            )
        )
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

        val recyclerView3 = onView(
            allOf(
                withId(R.id.rv_book_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(3, click()))

        val materialButton6 = onView(
            allOf(
                withId(R.id.btn_add_to_worklist), withContentDescription("Add"),
                childAtPosition(
                    allOf(
                        withId(R.id.headConstraintLayout),
                        childAtPosition(
                            withId(R.id.work_details),
                            0
                        )
                    ),
                    0
                )
            )
        )
        materialButton6.perform(scrollTo(), click())

        val recyclerView4 = onView(
            allOf(
                withId(R.id.rv_worklist_list),
                childAtPosition(
                    withId(R.id.constraintLayout),
                    2
                )
            )
        )
        recyclerView4.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatImageButton2 = onView(
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
        appCompatImageButton2.perform(click())

        val recyclerView5 = onView(
            allOf(
                withId(R.id.rv_book_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView5.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val materialButton7 = onView(
            allOf(
                withId(R.id.btn_add_to_worklist), withContentDescription("Add"),
                childAtPosition(
                    allOf(
                        withId(R.id.headConstraintLayout),
                        childAtPosition(
                            withId(R.id.work_details),
                            0
                        )
                    ),
                    0
                )
            )
        )
        materialButton7.perform(scrollTo(), click())

        val recyclerView6 = onView(
            allOf(
                withId(R.id.rv_worklist_list),
                childAtPosition(
                    withId(R.id.constraintLayout),
                    2
                )
            )
        )
        recyclerView6.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatImageButton3 = onView(
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
        appCompatImageButton3.perform(click())

        val bottomNavigationItemView3 = onView(
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
        bottomNavigationItemView3.perform(click())

        val recyclerView7 = onView(
            allOf(
                withId(R.id.rv_worklist_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView7.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView = onView(
            allOf(
                withId(R.id.work_title), withText("The Great Gatsby"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("The Great Gatsby")))

        val textView2 = onView(
            allOf(
                withId(R.id.work_title), withText("The Catcher in the Rye"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("The Catcher in the Rye")))

        val textView3 = onView(
            allOf(
                withId(R.id.work_title), withText("To Kill a Mockingbird"),
                withParent(withParent(withId(R.id.discover_cv_Item))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("To Kill a Mockingbird")))

        val recyclerView8 = onView(
            allOf(
                withId(R.id.rv_worklist_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView8.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView5 = onView(
            allOf(
                withId(R.id.workTitle), withText("The Great Gatsby"),
                withParent(
                    allOf(
                        withId(R.id.headConstraintLayout),
                        withParent(withId(R.id.work_details))
                    )
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("The Great Gatsby")))

        val appCompatImageButton4 = onView(
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
        appCompatImageButton4.perform(click())

        val recyclerView9 = onView(
            allOf(
                withId(R.id.rv_worklist_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView9.perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        val textView6 = onView(
            allOf(
                withId(R.id.workTitle), withText("The Catcher in the Rye"),
                withParent(
                    allOf(
                        withId(R.id.headConstraintLayout),
                        withParent(withId(R.id.work_details))
                    )
                ),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("The Catcher in the Rye")))

        val appCompatImageButton5 = onView(
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
        appCompatImageButton5.perform(click())

        val recyclerView10 = onView(
            allOf(
                withId(R.id.rv_worklist_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView10.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        val textView7 = onView(
            allOf(
                withId(R.id.workTitle), withText("To Kill a Mockingbird"),
                withParent(
                    allOf(
                        withId(R.id.headConstraintLayout),
                        withParent(withId(R.id.work_details))
                    )
                ),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("To Kill a Mockingbird")))
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

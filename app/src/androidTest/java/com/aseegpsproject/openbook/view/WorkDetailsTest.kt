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
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class WorkDetailsTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun workDetailsTest() {
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

        val recyclerView = onView(
            allOf(
                withId(R.id.rv_book_list),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        Thread.sleep(1000)

        val textView = onView(
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
        textView.check(matches(withText("To Kill a Mockingbird")))

        val textView2 = onView(
            allOf(
                withId(R.id.workDescription),
                withText("A novel about the injustices of the prejudiced social hierarchy in the 20th century society."),
                withParent(
                    allOf(
                        withId(R.id.cardView5),
                        withParent(withId(R.id.headConstraintLayout))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("A novel about the injustices of the prejudiced social hierarchy in the 20th century society.")))

        val button = onView(
            allOf(
                withId(R.id.btn_add_to_worklist), withContentDescription("Add"),
                withParent(
                    allOf(
                        withId(R.id.headConstraintLayout),
                        withParent(withId(R.id.work_details))
                    )
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.btn_favorite), withContentDescription("Favourite"),
                withParent(
                    allOf(
                        withId(R.id.headConstraintLayout),
                        withParent(withId(R.id.work_details))
                    )
                ),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.workCover), withContentDescription("cover"),
                withParent(
                    allOf(
                        withId(R.id.cardView),
                        withParent(withId(R.id.headConstraintLayout))
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.starImage), withContentDescription("star"),
                withParent(
                    allOf(
                        withId(R.id.headConstraintLayout),
                        withParent(withId(R.id.work_details))
                    )
                ),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withText("Work details"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Work details")))
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

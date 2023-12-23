package com.aseegpsproject.openbook.view


import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
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
class DeleteWorksListTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun deleteWorksListTest() {
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

        val textView1 = onView(
            allOf(
                withId(com.google.android.material.R.id.navigation_bar_item_large_label_view),
                withParent(withParent(withId(R.id.profileFragment)))
            )
        )
        textView1.check(matches(withText(R.string.profile_option)))

        val materialButton3 = onView(withId(R.id.btn_add_worklist))
        materialButton3.perform(click())

        val textView2 = onView(withId(R.id.tv_create_worklist))
        textView2.check(matches(withText(R.string.create_worklist)))

        val viewGroup = onView(withParent(withId(R.id.cv_create_worklist)))
        viewGroup.check(matches(isDisplayed()))

        val appCompatEditText4 = onView(withId(R.id.et_worklist_name))
        appCompatEditText4.perform(replaceText("dummyTestDeleteWorksList"), closeSoftKeyboard())

        val materialButton4 = onView(withId(R.id.btn_create_worklist))
        materialButton4.perform(click())

        val linearLayout = onView(
            childAtPosition(
                withClassName(`is`("androidx.recyclerview.widget.RecyclerView")),
                0
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tv_worklist_name), withText("dummyTestDeleteWorksList"),
                withParent(withParent(withId(R.id.cvWorklistItem))),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val text = getText(
            allOf(
                withId(R.id.tv_worklist_name),
                withParent(withParent(withId(R.id.cvWorklistItem))),
                withParent(withParent(withParentIndex(0))),
                isDisplayed()
            )
        )

        val recyclerView = onView(withId(R.id.rv_worklist_list))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, longClick()))


        val linearLayout1 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.cvWorklistItem),
                        withParent(withId(R.id.rv_worklist_list)),
                        withParentIndex(0)
                    )
                ),
                isDisplayed()
            )
        )
        try {
            val text1 = getText(
                allOf(
                    withId(R.id.tv_worklist_name),
                    withParent(withParent(withId(R.id.cvWorklistItem))),
                    withParent(withParentIndex(0)),
                    isDisplayed()
                )
            )

            assert(!text1.equals(text))
        } catch (e: Exception) {
            linearLayout1.check(doesNotExist())
        }
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

    private fun getText(matcher: Matcher<View>?): String? {
        val stringHolder = arrayOf<String?>(null)
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController?, view: View) {
                val tv = view as TextView
                stringHolder[0] = tv.text.toString()
            }
        })
        return stringHolder[0]
    }
}
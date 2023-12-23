package com.aseegpsproject.openbook.view


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.aseegpsproject.openbook.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateWorksListTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun createWorksListTest() {
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

        val textView = onView(
            allOf(
                withId(com.google.android.material.R.id.navigation_bar_item_large_label_view),
                withParent(withParent(withId(R.id.profileFragment)))
            )
        )
        textView.check(matches(withText(R.string.profile_option)))

        val materialButton3 = onView(withId(R.id.btn_add_worklist))
        materialButton3.perform(click())

        val textView2 = onView(withId(R.id.tv_create_worklist))
        textView2.check(matches(withText(R.string.create_worklist)))

        val viewGroup = onView(withParent(withId(R.id.cv_create_worklist)))
        viewGroup.check(matches(isDisplayed()))

        val appCompatEditText4 = onView(withId(R.id.et_worklist_name))
        appCompatEditText4.perform(replaceText("dummyTestWorksList"), closeSoftKeyboard())

        val materialButton4 = onView(withId(R.id.btn_create_worklist))
        materialButton4.perform(click())

        val linearLayout = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.cvWorklistItem),
                        withParent(withId(R.id.rv_worklist_list))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withId(R.id.tv_worklist_name),
                withText("dummyTestWorksList")
            )
        )
        textView3.check(matches(isDisplayed()))
    }
}

/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ankitgh.employeeportal.ui.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.ui.LauncherActivity
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(LauncherActivity::class.java)

    @Before
    fun setup() {
        onView(allOf(withId(R.id.lgs_start_button), withText("Start"), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.root_login_fragment), withContentDescription("root_login_fragment"), isDisplayed())).check(matches(isDisplayed()))
    }

    @Test
    fun visiblity_loginfields_areVisible() {
        onView(allOf(withId(R.id.login_email_input_edittext), withContentDescription("login_email_ed"), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.login_password_input_edittext), withContentDescription("login_password_ed"), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.login_button_signin), withContentDescription("login_button_signin"), isDisplayed())).check(matches(isDisplayed()))
    }

    @Test
    fun emptyfields_usernameAndPassword_showError() {
        onView(allOf(withId(R.id.login_email_input_edittext), withContentDescription("login_email_ed"), isDisplayed())).perform(typeText("")).perform(closeSoftKeyboard())
        onView(allOf(withId(R.id.login_password_input_edittext), withContentDescription("login_password_ed"), isDisplayed())).perform(typeText("")).perform(closeSoftKeyboard())
        onView(allOf(withId(R.id.login_button_signin), withContentDescription("login_button_signin"), isDisplayed())).perform(click())
        //onView(allOf(withId(R.id.login_email_input_layout), isDisplayed())).check(matches())
    }
}
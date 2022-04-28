package com.example.tasklisttaker.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tasklisttaker.R
import com.example.tasklisttaker.ui.detail.DetailTaskActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailTaskActivityTest {



        @get:Rule()
        val activity = ActivityScenarioRule(DetailTaskActivity::class.java)

        @Test
        fun calculate_default_tip() {
            onView(withId(R.id.importance_task_swtich_detail))
                .perform(click())

            onView(withId(R.id.task_completed_switch_detail)).perform(click())

            onView(withId(R.id.task_note_detail)).perform(typeText("do something"))


//            onView(withId(R.id.task_note_detail))
//                .check(matches(withText(containsString("10.00"))))
        }



}
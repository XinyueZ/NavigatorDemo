package com.demo.navigator;

import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import com.demo.navigator.home.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Testing opening and closing {@link android.support.v4.widget.DrawerLayout} by click "close".
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class NavigationDrawerCloseButtonTest {

	@Rule public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


	@Test
	public void testOnDrawerOpen() throws Exception {
		onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be opened.
		                                  .perform(open());
		onView(withId(R.id.action_close_navigator)) // Left Drawer should be closed by clicking close.
		                                            .perform(click());
	}

}

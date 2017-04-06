

package com.demo.navigator;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import com.demo.navigator.home.MainActivity;

import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.demo.navigator.actions.CustomizedActions.waitId;
import static com.demo.navigator.actions.CustomizedActions.withToolbarTitle;

/**
 * Testing workflow
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class NavigationWorkflow_2_Test {

	@Rule public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


	@Test
	public void testOnDrawer() throws Exception {
		onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)))
		                                  .perform(open()); // Left Drawer should be closed.
		onView(isRoot()).perform(waitId(R.id.entry_content_rv, TimeUnit.SECONDS.toMillis(30)));
		onView(withText("Alter")).perform(click());
		TimeUnit.SECONDS.sleep(3);
		onView(withText("Baby & Kleinkind")).perform(click());
		TimeUnit.SECONDS.sleep(3);
		onView(ViewMatchers.withId(R.id.menu_bar)).check(matches(withToolbarTitle(Is.<CharSequence> is("Baby & Kleinkind"))));
		TimeUnit.SECONDS.sleep(3);
		onView(withContentDescription(R.string.navigate_up)).perform(click()); //up
		TimeUnit.SECONDS.sleep(3);
		onView(ViewMatchers.withId(R.id.menu_bar)).check(matches(withToolbarTitle(Is.<CharSequence> is("Alter"))));
		TimeUnit.SECONDS.sleep(3);
		onView(withContentDescription(R.string.navigate_up)).perform(click()); //up
		TimeUnit.SECONDS.sleep(3);
		onView(ViewMatchers.withId(R.id.menu_bar)).check(matches(withToolbarTitle(Is.<CharSequence> is("Menu"))));
		TimeUnit.SECONDS.sleep(3);
		onView(withId(R.id.action_close_navigator)) // Left Drawer should be closed by clicking close.
		                                            .perform(click());
		onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)));
	}
}

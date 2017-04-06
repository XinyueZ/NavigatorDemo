package com.demo.navigator.actions;


import android.support.annotation.IdRes;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public final class CustomizedActions {
	/**
	 * Try to match text on a  {@link Toolbar}.
	 */
	public  static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
		return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
			@Override
			public boolean matchesSafely(Toolbar toolbar) {
				return textMatcher.matches(toolbar.getTitle());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("with toolbar title: ");
				textMatcher.describeTo(description);
			}
		};
	}


	/**
	 * A solution to wait a {@link View} with id until {@link android.support.test.espresso.Espresso} finds it.
	 * <a href="http://stackoverflow.com/questions/21417954/espresso-thread-sleep">Stackoverflow</a>
	 */
	public static ViewAction waitId(final @IdRes int viewId, final long millis) {
		return new ViewAction() {
			@Override
			public Matcher<View> getConstraints() {
				return isRoot();
			}

			@Override
			public String getDescription() {
				return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
			}

			@Override
			public void perform(final UiController uiController, final View view) {
				uiController.loopMainThreadUntilIdle();
				final long startTime = System.currentTimeMillis();
				final long endTime = startTime + millis;
				final Matcher<View> viewMatcher = withId(viewId);

				do {
					for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
						// found view with required ID
						if (viewMatcher.matches(child)) {
							return;
						}
					}

					uiController.loopMainThreadForAtLeast(50);
				} while (System.currentTimeMillis() < endTime);

				// timeout happens
				throw new PerformException.Builder().withActionDescription(this.getDescription())
				                                    .withViewDescription(HumanReadables.describe(view))
				                                    .withCause(new TimeoutException())
				                                    .build();
			}
		};
	}
}

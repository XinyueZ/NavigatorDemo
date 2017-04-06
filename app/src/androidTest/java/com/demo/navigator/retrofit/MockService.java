package com.demo.navigator.retrofit;

import com.demo.navigator.ds.model.NavigationEntries;
import com.google.gson.Gson;

import io.reactivex.Observable;
import retrofit2.mock.BehaviorDelegate;

/**
 * To improve performance when testing we use local data (mock data) to simplify testing.
 */
public final class MockService implements Service {
	private final BehaviorDelegate<Service> mDelegate;
	private static final String FEEDS = "{\n" + "\t\"navigationEntries\": [{\n" + "\t\t\"type\": \"section\",\n" + "\t\t\"label\": \"Sortiment\",\n" + "\t\t\"children\": [{\n" + "\t\t\t\"type\": " +
			"\"node\",\n" + "\t\t\t\"label\": \"Alter\",\n" + "\t\t\t\"children\": [{\n" + "\t\t\t\t\"type\": \"node\",\n" + "\t\t\t\t\"label\": \"Baby & Kleinkind\",\n" + "\t\t\t\t\"children\": " +
			"[{\n" + "\t\t\t\t\t\"type\": \"link\",\n" + "\t\t\t\t\t\"label\": \"0-6 Monate\",\n" + "\t\t\t\t\t\"url\": \"http:\\/\\/www.mytoys.de\\/0-6-months\\/\"\n" + "\t\t\t\t}, {\n" +
			"\t\t\t\t\t\"type\": \"external-link\",\n" + "\t\t\t\t\t\"label\": \"7-12 Monate\",\n" + "\t\t\t\t\t\"url\": \"http:\\/\\/www.mytoys.de\\/7-12-months\\/\"\n" + "\t\t\t\t}]\n" +
			"\t\t\t}]\n" + "\t\t}]\n" + "\t}]\n" + "}\n";

	public MockService(BehaviorDelegate<Service> delegate) {
		this.mDelegate = delegate;
	}

	public Observable<NavigationEntries> getNavigationEntries() {
		return mDelegate.returningResponse(new Gson().fromJson(FEEDS, NavigationEntries.class))
		                .getNavigationEntries();
	}
}

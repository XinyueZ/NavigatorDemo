package com.demo.navigator.retrofit;

import com.demo.navigator.ds.model.NavigationEntries;
import com.google.gson.Gson;

import io.reactivex.Observable;
import retrofit2.mock.BehaviorDelegate;

public final class MockService implements Service {
	private final BehaviorDelegate<Service> mDelegate;
	private static final String FEEDS = "{\n" + "    \"navigationEntries\": [ {\n" + "        \"type\": \"section\",\n" + "        \"label\": \"Schn\\u00e4ppchen\",\n" + "        \"children\": [{\n"
			+ "            \"type\": \"link\",\n" + "            \"label\": \"Alle Angebote im \\u00dcberblick\",\n" + "            \"url\": \"http:\\/\\/www.mytoys.de\\/sale\\/\"\n" + "        } "
			+ "]\n" + "    } ]\n" + "}";

	public MockService(BehaviorDelegate<Service> delegate) {
		this.mDelegate = delegate;
	}

	public Observable<NavigationEntries> getNavigationEntries() {
		return mDelegate.returningResponse(new Gson().fromJson(FEEDS, NavigationEntries.class))
		                .getNavigationEntries();
	}
}

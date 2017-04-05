package com.demo.navigator.ds;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xzhao on 05.04.17.
 */

public final class NavigationEntries {
	@SerializedName("children") private List<Entry> mEntries;

	public NavigationEntries(List<Entry> entries) {
		mEntries = entries;
	}

	public List<Entry> getEntries() {
		return mEntries;
	}

	public void setEntries(List<Entry> entries) {
		mEntries = entries;
	}
}
